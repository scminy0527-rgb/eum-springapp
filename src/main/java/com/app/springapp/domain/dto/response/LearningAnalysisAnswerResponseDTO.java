package com.app.springapp.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "AI 학습 분석 문제별 정답 응답 DTO")
public class LearningAnalysisAnswerResponseDTO {
    @Schema(description = "문제 번호")
    private Long questionNumber;

    @JsonProperty("isCorrect")
    @Schema(description = "정답 여부")
    private Boolean isCorrect;
}