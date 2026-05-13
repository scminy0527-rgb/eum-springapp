package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.ChatUserDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "채팅방 참여 현황 응답 DTO")
public class ChatUserResponseDTO {
    @Schema(description = "채팅 멤버 번호", example = "1")
    private Long id;
    @Schema(description = "채팅 참여 시작 일시", example = "2024-01-01T00:00:00")
    private LocalDateTime chatStartAt;
    @Schema(description = "마지막 읽은 일시", example = "2024-01-01T00:00:00")
    private LocalDateTime chatLastReadAt;
    @Schema(description = "유저 번호", example = "1")
    private Long userId;
    @Schema(description = "채팅방 번호", example = "1")
    private Long chatRoomId;

    public static ChatUserResponseDTO from(ChatUserDTO dto) {
        ChatUserResponseDTO res = new ChatUserResponseDTO();
        res.setId(dto.getId());
        res.setChatStartAt(dto.getChatStartAt());
        res.setChatLastReadAt(dto.getChatLastReadAt());
        res.setUserId(dto.getUserId());
        res.setChatRoomId(dto.getChatRoomId());
        return res;
    }
}
