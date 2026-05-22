package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.SignWordResponseDTO;
import com.app.springapp.domain.dto.response.SignWordXmlResponseDTO;
import com.app.springapp.domain.vo.SignWordVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.SignWordDAO;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class SignWordServiceImpl implements SignWordService {
    private final SignWordDAO signWordDAO;
    private final RestTemplate restTemplate;

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



    // 문화공공데이터 수어 검색
    // OpenAPI 수어 데이터 동기화
    @Override
    public int syncSignWords(int pageNo, int numOfRows) {
        String xml = requestSignWordOpenApi("", pageNo, numOfRows);
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

            if (responseDTO.getBody() == null || responseDTO.getBody().getItems() == null) {
                return List.of();
            }

            return responseDTO.getBody().getItems().stream()
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

    // 이미지/영상 URL이 비어 있으면 빈 문자열로 처리하고, http 주소는 https로 변환
    private String changeHttps(String url) {
        if (url == null || url.isBlank()) {
            return "";
        }

        return url.replace("http://", "https://");
    }
}

