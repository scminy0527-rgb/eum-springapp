package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "채팅방 신고 요청 DTO")
public class ChatRoomReportRequestDTO {
    @Schema(description = "신고 제목", example = "부적절한 채팅방", required = true)
    private String chatRoomReportTitle;
    @Schema(description = "신고 내용", example = "불법 정보가 공유되는 채팅방입니다.", required = true)
    private String chatRoomReportDetail;
//    @Schema(description = "신고자 유저 번호", example = "1", required = true)
//    private Long userId;
    @Schema(description = "신고 대상 채팅방 번호", example = "1", required = true)
    private Long chatRoomId;
}
