package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "시험 신청 DTO")
public class TestApplyDTO {
    @Schema(description = "신청 번호", example = "1")
    private Long id;
    @Schema(description = "신청 일시", example = "2024-01-01T00:00:00")
    private LocalDateTime testApplyAt;
    @Schema(description = "유저 번호", example = "1")
    private Long userId;
    @Schema(description = "시험 번호", example = "1")
    private Long testId;

    // 내 접수 목록 조회 시 JOIN으로 채워지는 시험 정보
    private String testTitle;
    private LocalDateTime testDate;
    private String testLocation;

    // 증빙서류 파일 경로 (콤마 구분)
    private String filePaths;
}
