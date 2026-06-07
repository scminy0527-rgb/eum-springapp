package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "자격증 시험 DTO")
public class TestDTO {
    @Schema(description = "시험 번호", example = "1")
    private Long id;
    @Schema(description = "시험 제목", example = "수어 자격증 1급")
    private String testTitle;
    @Schema(description = "시험 설명", example = "수어 통역사 자격증 1급 시험입니다.")
    private String testDetail;
    @Schema(description = "시험 일시", example = "2024-06-01T10:00:00")
    private LocalDateTime testDate;
    @Schema(description = "시험 정원", example = "60")
    private int testLimit;
    @Schema(description = "시험 장소", example = "서울특별시 강남구 테헤란로 123")
    private String testLocation;
    @Schema(description = "시험 응시료", example = "50000")
    private int testPrice;
    @Schema(description = "접수 시작일", example = "2024-05-01T00:00:00")
    private LocalDateTime testReceiptStart;
    @Schema(description = "접수 종료일", example = "2024-05-14T23:59:59")
    private LocalDateTime testReceiptEnd;
    @Schema(description = "삭제 여부 (0: 미삭제, 1: 삭제)", example = "0")
    private int testIsDeleted;
}
