package com.app.springapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

//  XML 파싱용 본문 DTO
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "OpenAPI 수어 XML 본문 DTO")
public class SignWordXmlBodyDTO {

    @JacksonXmlElementWrapper(useWrapping = false) //  items -> 큰 박스
    @JacksonXmlProperty(localName = "items") // 큰 박스 -> item을 리스트로 담음
    @Schema(description = "수어 OpenAPI items 목록")
    private SignWordXmlItemDTO items;

    @JacksonXmlProperty(localName = "totalCount")
    @Schema(description = "전체 수어 데이터 개수", example = "3753")
    private int totalCount;
}