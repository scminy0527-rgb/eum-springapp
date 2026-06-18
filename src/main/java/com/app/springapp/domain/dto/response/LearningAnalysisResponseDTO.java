package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@Schema(description = "AI 학습 분석 응답 DTO")
public class LearningAnalysisResponseDTO {
    @Schema(description = "종합 리포트")
    private LearningAnalysisReportResponseDTO report;

    @Schema(description = "전체 분석 목록")
    private List<LearningOverallAnalysisResponseDTO> overallAnalysisList = new ArrayList<>();

    @Schema(description = "개별 분석 목록")
    private List<LearningIndividualAnalysisResponseDTO> individualAnalysisList = new ArrayList<>();
}