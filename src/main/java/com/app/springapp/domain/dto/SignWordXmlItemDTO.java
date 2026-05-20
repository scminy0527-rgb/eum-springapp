package com.app.springapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

//  XML 파싱용 DTO
@Data
// 모르는 XML 태그는 무시
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignWordXmlItemDTO {

    @JacksonXmlProperty(localName = "title")
    private String title;

    @JacksonXmlProperty(localName = "referenceIdentifier")
    private String referenceIdentifier;

    @JacksonXmlProperty(localName = "subDescription")
    private String subDescription;

    @JacksonXmlProperty(localName = "signDescription")
    private String signDescription;

    @JacksonXmlProperty(localName = "signImages")
    private String signImages;

    @JacksonXmlProperty(localName = "categoryType")
    private String categoryType;

    @JacksonXmlProperty(localName = "url")
    private String url;
}