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

    @JacksonXmlElementWrapper(localName = "items") //  items -> 큰 박스
    @JacksonXmlProperty(localName = "item") // 큰 박스 -> item을 리스트로 담음
    @Schema(description = "수어 단어 목록")
    private List<SignWordXmlItemDTO> items;

    @JacksonXmlProperty(localName = "totalCount")
    @Schema(description = "전체 수어 단어 개수", example = "299")
    private int totalCount;
}