package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@Schema(description = "AI 학습 개별 분석 응답 DTO")
public class LearningIndividualAnalysisResponseDTO {
    @Schema(description = "학습 결과 번호")
    private Long id;

    @Schema(description = "학습 결과 제목")
    private String title;

    @Schema(description = "학습 카테고리")
    private String category;

    @Schema(description = "정답률")
    private Long rate;

    @Schema(description = "학습 시간")
    private String studyTime;

    @Schema(description = "문제 수")
    private Long questionCount;

    @Schema(description = "표시 색상")
    private String color;

    @Schema(description = "문제별 O/X 결과")
    private List<String> answers = new ArrayList<>();

    @Schema(description = "AI 추천 학습 가이드")
    private String guide;
}