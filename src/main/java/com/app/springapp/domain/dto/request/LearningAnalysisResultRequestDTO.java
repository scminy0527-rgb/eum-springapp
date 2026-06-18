package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@Schema(description = "AI 학습 분석 학습 결과 요청 DTO")
public class LearningAnalysisResultRequestDTO {
    @Schema(description = "학습 결과 번호")
    private Long id;

    @Schema(description = "학습 결과 제목")
    private String title;

    @Schema(description = "학습 카테고리")
    private String category;

    @Schema(description = "정답 수")
    private Long correctCount;

    @Schema(description = "전체 문제 수")
    private Long totalCount;

    @Schema(description = "학습 시간 분 단위")
    private Long studyTimeMinutes;

    @Schema(description = "문제별 정답 정보")
    private List<LearningAnalysisAnswerRequestDTO> answers = new ArrayList<>();
}