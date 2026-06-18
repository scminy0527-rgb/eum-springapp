package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@Schema(description = "AI 학습 분석 요청 DTO")
public class LearningAnalysisRequestDTO {
    @Schema(description = "회원 번호")
    private Long userId;

    @Schema(description = "연속 학습 일수")
    private Long streakDays = 0L;

    @Schema(description = "경험치")
    private Long exp = 0L;

    @Schema(description = "학습 결과 목록")
    private List<LearningAnalysisResultRequestDTO> results = new ArrayList<>();
}