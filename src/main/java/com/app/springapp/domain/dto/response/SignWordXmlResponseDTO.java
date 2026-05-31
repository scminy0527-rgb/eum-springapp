package com.app.springapp.domain.dto.response;

import com.app.springapp.domain.dto.SignWordXmlBodyDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//  XML 파싱용 응답 DTO
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "OpenAPI 수어 XML 응답 DTO")
public class SignWordXmlResponseDTO {

    @JacksonXmlProperty(localName = "body")
    @Schema(description = "OpenAPI 수어 응답 본문")
    private SignWordXmlBodyDTO body;
}