package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "OpenAPI 수어 데이터를 학습 단어로 등록 요청 DTO")
public class EduWordFromSignWordRequestDTO {
    // signWord를 학습 words로 변환해서 연결
    @Schema(description = "학습 번호", example = "1", required = true)
    private Long eduId;
    @Schema(description = "수어 OpenAPI 단어 번호", example = "5", required = true)
    private Long signWordId;
    @Schema(description = "학습 단어 타입", example = "기초", required = true)
    private String wordsType;
}
