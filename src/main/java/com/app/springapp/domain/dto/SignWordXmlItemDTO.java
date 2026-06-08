package com.app.springapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

//  XML 파싱용 수어 단어 DTO
@Data
// 모르는 XML 태그는 무시
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "OpenAPI 수어 XML 단어 DTO")
public class SignWordXmlItemDTO {

    @JacksonXmlProperty(localName = "title")
    @Schema(description = "수어 단어명", example = "호랑이,범")
    private String title;

    @JacksonXmlProperty(localName = "referenceIdentifier")
    @Schema(description = "OpenAPI 수어 식별자", example = "1017")
    private String referenceIdentifier;

    @JacksonXmlProperty(localName = "subDescription")
    @Schema(description = "수어 보조 설명")
    private String subDescription;

    @JacksonXmlProperty(localName = "signDescription")
    @Schema(description = "수어 동작 설명")
    private String signDescription;

    @JacksonXmlProperty(localName = "signImages")
    @Schema(description = "수어 이미지 주소")
    private String signImages;

    @JacksonXmlProperty(localName = "categoryType")
    @Schema(description = "수어 카테고리", example = "동식물")
    private String categoryType;

    @JacksonXmlProperty(localName = "url")
    @Schema(description = "수어 원본 페이지 주소")
    private String url;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    @Schema(description = "수어 OpenAPI item 목록")
    private List<SignWordXmlItemDTO> item;

}