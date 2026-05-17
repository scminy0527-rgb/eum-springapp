package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.NoticeDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "공지사항 응답 DTO")
public class NoticeResponseDTO {
    @Schema(description = "공지사항 번호", example = "1")
    private Long id;
    @Schema(description = "공지사항 제목", example = "서비스 점검 안내")
    private String noticeTitle;
    @Schema(description = "공지사항 내용", example = "2024년 1월 1일 서비스 점검이 예정되어 있습니다.")
    private String noticeContent;
    @Schema(description = "공지사항 생성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime noticeCreateAt;
    @Schema(description = "관리자 번호", example = "1")
    private Long userId;

    public static NoticeResponseDTO from(NoticeDTO dto) {
        NoticeResponseDTO res = new NoticeResponseDTO();
        res.setId(dto.getId());
        res.setNoticeTitle(dto.getNoticeTitle());
        res.setNoticeContent(dto.getNoticeContent());
        res.setNoticeCreateAt(dto.getNoticeCreateAt());
        res.setUserId(dto.getUserId());
        return res;
    }
}
