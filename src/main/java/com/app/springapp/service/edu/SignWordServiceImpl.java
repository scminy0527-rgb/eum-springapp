package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.SignWordResponseDTO;
import com.app.springapp.domain.dto.response.SignWordXmlResponseDTO;
import com.app.springapp.exception.EduException;
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
    @Value("${culture.api.sign-word.base-url}")
    private String baseUrl;

    @Value("${culture.api.sign-word.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    // 1. 외부 API 주소 만들기
    // 2. 문화공공데이터 API 호출하기
    // 3. XML 결과 받기

    // 문화공공데이터 수어 검색
    @Override
    public List<SignWordResponseDTO> searchSignWords(String keyword, int pageNo, int numOfRows) {
        String url = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("keyword", keyword == null ? "" : keyword)
//                .queryParam("_type", "json")
//                .queryParam("collectionDb", "")
                .toUriString();
        log.info("문화 API 요청 URL = {}", url);

        // 외부API호출결과
        String xml = restTemplate.getForObject(url, String.class);
        return convertXmlToSignWords(xml);
    }

    // 4. XML을 DTO 리스트로 바꿔서 반환하기
    private List<SignWordResponseDTO> convertXmlToSignWords(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            SignWordXmlResponseDTO responseDTO = xmlMapper.readValue(xml, SignWordXmlResponseDTO.class);

            if (responseDTO.getBody() == null || responseDTO.getBody().getItems() == null) {
                return List.of();
            }

            return responseDTO.getBody().getItems().stream()
                    .map(item -> {
                        SignWordResponseDTO dto = new SignWordResponseDTO();
                        dto.setTitle(item.getTitle());
                        dto.setThumbnailUrl(changeHttps(item.getReferenceIdentifier()));
                        dto.setVideoUrl(changeHttps(item.getSubDescription()));
                        dto.setSignDescription(item.getSignDescription());
                        dto.setSignImages(item.getSignImages());
                        dto.setCategoryType(item.getCategoryType());
                        dto.setSourceUrl(item.getUrl());
                        return dto;
                    })
                    .toList();

        } catch (Exception e) {
            throw new EduException("수어 OpenAPI 응답 변환 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // changeHttps 보조
    // 이미지/영상 URL이 비어 있으면 빈 문자열로 처리하고, http 주소는 https로 변환
    private String changeHttps(String url) {
        if (url == null || url.isBlank()) {
            return "";
        }

        return url.replace("http://", "https://");
    }
}

