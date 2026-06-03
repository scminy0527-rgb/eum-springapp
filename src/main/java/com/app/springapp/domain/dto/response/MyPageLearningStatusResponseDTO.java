package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "마이페이지 학습현황 응답 DTO")
public class MyPageLearningStatusResponseDTO {
    @Schema(description = "학습 번호", example = "1", required = true)
    private Long eduId;

    @Schema(description = "학습 제목", example = "수어 기초", required = true)
    private String eduTitle;

    @Schema(description = "학습 진행률", example = "70", required = true)
    private Long progress;

    @Schema(description = "학습 시간(초)", example = "7200", required = true)
    private Long studyTime;

    @Schema(description = "최근 학습 일시", example = "2026-06-03T10:00:00")
    private LocalDateTime recentStudyAt;
}