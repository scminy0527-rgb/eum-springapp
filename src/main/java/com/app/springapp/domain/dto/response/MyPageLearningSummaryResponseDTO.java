package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "마이페이지 학습요약 응답 DTO")
public class MyPageLearningSummaryResponseDTO {
    @Schema(description = "총 정답률", example = "75", required = true)
    private Long totalAccuracy;

    @Schema(description = "총 푼 문제 수", example = "55", required = true)
    private Long totalQuestionCount;

    @Schema(description = "총 학습시간(초)", example = "9000", required = true)
    private Long totalStudyTime;
}