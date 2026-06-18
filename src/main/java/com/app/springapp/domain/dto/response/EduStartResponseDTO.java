package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.EduStartDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "학습 시작 내역 응답 DTO")
public class EduStartResponseDTO {
    @Schema(description = "학습 시작 번호", example = "1")
    private Long id;
    @Schema(description = "학습 시작 일시", example = "2024-01-01T00:00:00")
    private LocalDateTime eduStartAt;
    @Schema(description = "유저 번호", example = "1")
    private Long userId;
    @Schema(description = "학습 번호", example = "1")
    private Long eduId;
    @Schema(description = "학습 완료 여부", example = "0")
    private int eduStartIsCompleted;
    @Schema(description = "학습 세션 전체 문제 수", example = "5")
    private int eduStartTotalCount;
    @Schema(description = "현재 학습 세션에서 푼 문제 수", example = "2")
    private int eduStartCompletedCount;
    @Schema(description = "현재 학습 세션에서 맞힌 문제 수", example = "2")
    private int eduStartCorrectCount;
    @Schema(description = "학습 세션 소요 시간: 초 단위", example = "140")
    private int eduStartTime;
    @Schema(description = "학습 완료 일시", example = "2026-06-16T15:06:48")
    private LocalDateTime eduStartCompletedAt;

    public static EduStartResponseDTO from(EduStartDTO dto) {
        EduStartResponseDTO res = new EduStartResponseDTO();
        res.setId(dto.getId());
        res.setEduStartAt(dto.getEduStartAt());
        res.setUserId(dto.getUserId());
        res.setEduId(dto.getEduId());
        res.setEduStartIsCompleted(dto.getEduStartIsCompleted());
        res.setEduStartTotalCount(dto.getEduStartTotalCount());
        res.setEduStartCompletedCount(dto.getEduStartCompletedCount());
        res.setEduStartCorrectCount(dto.getEduStartCorrectCount());
        res.setEduStartTime(dto.getEduStartTime());
        res.setEduStartCompletedAt(dto.getEduStartCompletedAt());
        return res;
    }
}
