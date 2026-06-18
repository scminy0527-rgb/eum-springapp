package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "AI 학습 전체 분석 응답 DTO")
public class LearningOverallAnalysisResponseDTO {
    @Schema(description = "분석 제목")
    private String title;

    @Schema(description = "정답률")
    private Long rate;

    @Schema(description = "표시 색상")
    private String color;

    @Schema(description = "분석 설명")
    private String description;

    @Schema(description = "추천 학습 문구")
    private String guide;

    @Schema(description = "위험 점수 여부")
    private boolean danger;
}