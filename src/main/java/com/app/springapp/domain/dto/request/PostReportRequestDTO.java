package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "게시글 신고 요청 DTO")
public class PostReportRequestDTO {
    @Schema(description = "신고 제목", example = "부적절한 게시글", required = true)
    private String postReportTitle;
    @Schema(description = "신고 내용", example = "욕설 및 혐오 표현이 포함된 게시글입니다.", required = true)
    private String postReportDetail;
//    @Schema(description = "신고자 유저 번호", example = "1", required = true)
//    private Long userId;
    @Schema(description = "신고 대상 게시글 번호", example = "1", required = true)
    private Long postId;
}
