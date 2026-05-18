package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "공지사항 DTO")
public class NoticeDTO {
    @Schema(description = "공지사항 번호", example = "1")
    private Long id;

    @Schema(description = "공지사항 제목", example = "서비스 점검 안내")
    private String noticeTitle;

    @Schema(description = "공지사항 내용", example = "점검 내용입니다.")
    private String noticeContent;

    @Schema(description = "카테고리", example = "공지")
    private String noticeCategory;

    @Schema(description = "상단 고정 여부", example = "0")
    private int noticePinned;

    @Schema(description = "첨부파일 URL", example = "default.jpg")
    private String noticeFileUrl;

    @Schema(description = "생성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime noticeCreateAt;

    @Schema(description = "작성자 번호", example = "1")
    private Long userId;

    // 페이징용
    @Schema(description = "시작 위치", example = "0")
    private int offset;

    @Schema(description = "페이지당 개수", example = "10")
    private int size;
}