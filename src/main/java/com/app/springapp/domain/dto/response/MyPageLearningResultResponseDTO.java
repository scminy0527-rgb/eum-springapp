package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "마이페이지 학습결과 응답 DTO")
public class MyPageLearningResultResponseDTO {
    @Schema(description = "퀴즈 응시 번호", example = "1", required = true)
    private Long quizAttemptId;

    @Schema(description = "퀴즈 번호", example = "1", required = true)
    private Long quizId;

    @Schema(description = "퀴즈 제목", example = "수어 기초 퀴즈", required = true)
    private String quizTitle;

    @Schema(description = "정답 수", example = "7", required = true)
    private Long correctCount;

    @Schema(description = "전체 문제 수", example = "10", required = true)
    private Long totalCount;

    @Schema(description = "소요시간(초)", example = "200", required = true)
    private Long spentTime;

    @Schema(description = "정답률", example = "70", required = true)
    private Long accuracy;

    @Schema(description = "응시 일시", example = "2026-06-03T10:00:00")
    private LocalDateTime quizAttemptCreateAt;
}