package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.SignWordXmlItemDTO;
import com.app.springapp.domain.dto.response.SignWordResponseDTO;
import com.app.springapp.domain.dto.response.SignWordXmlResponseDTO;
import com.app.springapp.domain.vo.SignWordVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.SignWordDAO;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class SignWordServiceImpl implements SignWordService {

    private final SignWordDAO signWordDAO;
    private final RestTemplate restTemplate;
    private final ChatClient chatClient;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${culture.api.sign-word.base-url}")
    private String baseUrl;

    @Value("${culture.api.sign-word.service-key}")
    private String serviceKey;

    // 수어 전체 조회
    @Override
    public List<SignWordResponseDTO> getSignWords() {
        return signWordDAO.findAll();
    }

    // 수어 검색 조회
    @Override
    public List<SignWordResponseDTO> getSignWordsByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return signWordDAO.findAll();
        }
        return signWordDAO.findByKeyword(keyword);
    }

    // 수어 상세 조회
    @Override
    public SignWordResponseDTO getSignWordById(Long id) {
        return signWordDAO.findById(id)
                .orElseThrow(() -> new EduException("수어 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 수어 등록
    @Override
    public void saveSignWord(SignWordVO signWordVO) {
        signWordDAO.save(signWordVO);
    }

    // 수어 수정
    @Override
    public void updateSignWord(SignWordVO signWordVO) {
        signWordDAO.update(signWordVO);
    }

    // 수어 삭제
    @Override
    public void deleteSignWord(Long id) {
        signWordDAO.delete(id);
    }

    // OpenAPI 수어 데이터 동기화
    @Override
    public int syncSignWords(int pageNo, int numOfRows) {
        String xml = requestSignWordOpenApi("", pageNo, numOfRows);
        System.out.println("sign word xml length = " + (xml == null ? 0 : xml.length()));
        System.out.println("sign word xml preview = " + (
                xml == null ? "null" : xml.substring(0, Math.min(xml.length(), 500))
        ));

        List<SignWordVO> signWords = convertXmlToSignWordVOs(xml);

        int savedCount = 0;

        for (SignWordVO signWordVO : signWords) {
            if (signWordDAO.findBySourceUrl(signWordVO.getSignWordSourceUrl()).isPresent()) {
                continue;
            }
            signWordDAO.save(signWordVO);
            savedCount++;
        }

        return savedCount;
    }

    // 오늘의 수어 영상 3개 (날짜 기반 + 이모지 생성) -> 학습 검색쪽에 넣어두기!
    @Cacheable(value = "todaySignWords", key = "T(java.time.LocalDate).now().toString()", unless = "#result == null || #result.isEmpty()")
    @Override
    public List<SignWordResponseDTO> getTodaySignWords() {
//        long seed = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+09:00")); // 1분뒤 뱐걍
        long seed = LocalDate.now().toEpochDay();
        List<SignWordResponseDTO> allWords = getSignWords();
        Collections.shuffle(allWords, new Random(seed));

        return allWords.stream()
                .limit(3)
                .map(word -> {
                    try {
                        String emoji = generateEmoji(word.getSignWordTitle());
                        word.setEmoji(emoji);
                    } catch (Exception e) {
                        log.warn("이모지 생성 실패 - title: {}, error: {}", word.getSignWordTitle(), e.getMessage());
                        word.setEmoji("🤟");
                    }
                    return word;
                })
                .collect(Collectors.toList());
    }

    // 캐시 키 (날짜 기준)
    public String todayCacheKey() {
        return LocalDate.now().toString();
    }

    // 자정마다 자동 초기화
//    @Scheduled(cron = "0 */1 * * * *") // 1분마다 캐쉬 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduledClearCache() {
        log.warn("=== 캐시 자동 초기화 실행 ===");
        redisTemplate.delete("todaySignWords::" + LocalDate.now().toString());
    }

        // 수동 캐시 초기화
        @CacheEvict(value = "todaySignWords", allEntries = true)
        @Override
        public void clearTodaySignWordsCache() {
            log.info("오늘의 수어 캐시 초기화");
        }

    // 문화공공데이터 OpenAPI 호출
    private String requestSignWordOpenApi(String keyword, int pageNo, int numOfRows) {
        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("keyword", keyword == null ? "" : keyword)
                .toUriString();

        log.info("문화 API 요청 URL = {}", url);

        return restTemplate.getForObject(url, String.class);
    }

    // OpenAPI XML 응답을 수어 VO 목록으로 변환
    private List<SignWordVO> convertXmlToSignWordVOs(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            SignWordXmlResponseDTO responseDTO = xmlMapper.readValue(xml, SignWordXmlResponseDTO.class);

            log.warn("파싱 결과 - body: {}, items: {}",
                    responseDTO.getBody(),
                    responseDTO.getBody() != null ? responseDTO.getBody().getItems() : "null");


            if (responseDTO.getBody() == null
                    || responseDTO.getBody().getItems() == null
                    || responseDTO.getBody().getItems().getItem() == null) {
                return List.of();
            }

            return responseDTO.getBody().getItems().getItem().stream()
                    .map(item -> {
                        SignWordVO signWordVO = new SignWordVO();
                        signWordVO.setSignWordTitle(item.getTitle());
                        signWordVO.setSignWordDescription(item.getSignDescription());
                        signWordVO.setSignWordCategory(item.getCategoryType());
                        signWordVO.setSignWordThumbnailUrl(changeHttps(item.getReferenceIdentifier()));
                        signWordVO.setSignWordVideoUrl(changeHttps(item.getSubDescription()));
                        signWordVO.setSignWordImages(changeHttps(item.getSignImages()));
                        signWordVO.setSignWordSourceUrl(changeHttps(item.getUrl()));
                        return signWordVO;
                    })
                    .toList();

        } catch (Exception e) {
            throw new EduException("수어 OpenAPI 응답 변환 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // OpenAI로 이모지 생성
    private String generateEmoji(String title) {
        String prompt = "\"" + title + "\"라는 한국 수어 단어를 가장 잘 표현하는 이모지 1개만 반환해줘. 이모지 외에 다른 텍스트는 절대 포함하지 마.";

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content()
                .trim();
    }

    // http → https 변환
    private String changeHttps(String url) {
        if (url == null || url.isBlank()) {
            return "";
        }
        return url.replace("http://", "https://");
    }
}