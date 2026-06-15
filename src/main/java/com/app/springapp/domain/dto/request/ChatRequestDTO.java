package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "채팅 메시지 전송 요청 DTO")
public class ChatRequestDTO {
    @Schema(description = "채팅 내용", example = "안녕하세요!", required = true)
    private String chatContent;
    @Schema(description = "채팅 타입", example = "텍스트", required = true)
    private String chatType;
    @Schema(description = "수어 keypoints", required = false)
    private Object keypoints;
}
