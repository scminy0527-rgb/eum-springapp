package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "유저 신고 요청 DTO")
public class UserReportRequestDTO {
    @Schema(description = "신고 제목", example = "욕설 사용", required = true)
    private String userReportTitle;
    @Schema(description = "신고 내용", example = "심한 욕설을 사용했습니다.", required = true)
    private String userReportDetail;
//    @Schema(description = "신고 대상 유저 번호", example = "1", required = true)
//    private Long userId;
    @Schema(description = "신고 대상 유저 번호", example = "2", required = true)
    private Long reportingUserId;
}
