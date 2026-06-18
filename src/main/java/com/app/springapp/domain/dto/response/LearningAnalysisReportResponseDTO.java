package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "AI 학습 분석 종합 리포트 응답 DTO")
public class LearningAnalysisReportResponseDTO {
    @Schema(description = "전체 정답률")
    private Long totalRate;

    @Schema(description = "분석 등급")
    private String grade;

    @Schema(description = "분석 메시지")
    private String message;

    @Schema(description = "평균 정답률")
    private String averageRate;

    @Schema(description = "총 문제 풀이")
    private String solvedCount;

    @Schema(description = "총 학습 시간")
    private String studyTime;

    @Schema(description = "연속 학습 일수")
    private String streakDays;

    @Schema(description = "획득 경험치")
    private String exp;
}