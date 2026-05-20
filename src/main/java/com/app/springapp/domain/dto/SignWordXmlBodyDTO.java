package com.app.springapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

//  XML 파싱용 DTO
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignWordXmlBodyDTO {

    @JacksonXmlElementWrapper(localName = "items") //  items -> 큰 박스
    @JacksonXmlProperty(localName = "item") // 큰 박스 -> item을 리스트로 담음

    private List<SignWordXmlItemDTO> items;

    @JacksonXmlProperty(localName = "totalCount")
    private int totalCount;
}