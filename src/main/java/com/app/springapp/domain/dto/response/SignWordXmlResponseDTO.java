package com.app.springapp.domain.dto.response;

import com.app.springapp.domain.dto.SignWordXmlBodyDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

//  XML 파싱용 DTO
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignWordXmlResponseDTO {

    @JacksonXmlProperty(localName = "body")
    private SignWordXmlBodyDTO body;
}