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
    private Long userId;
    private Long chatRoomId;
    private String userNickname;
    private String userProfile;
    private String userEmail;
    private Long userExp;

    public static ChatUserResponseDTO from(ChatUserDTO dto) {
        ChatUserResponseDTO res = new ChatUserResponseDTO();
        res.setId(dto.getId());
        res.setUserId(dto.getUserId());
        res.setChatRoomId(dto.getChatRoomId());
        res.setUserNickname(dto.getUserNickname());
        res.setUserProfile(dto.getUserProfile());
        res.setUserEmail(dto.getUserEmail());
        res.setUserExp(dto.getUserExp());

        return res;
    }
}
