package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "댓글 신고 요청 DTO")
public class CommentReportRequestDTO {
    @Schema(description = "신고 제목", example = "부적절한 댓글", required = true)
    private String commentReportTitle;
    @Schema(description = "신고 내용", example = "욕설이 포함된 댓글입니다.", required = true)
    private String commentReportDetail;
//    @Schema(description = "신고자 유저 번호", example = "1", required = true)
//    private Long userId;
    @Schema(description = "신고 대상 댓글 번호", example = "1", required = true)
    private Long commentId;
}
