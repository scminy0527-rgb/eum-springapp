package com.app.springapp.domain.dto.response;

import com.app.springapp.domain.dto.ChatRoomDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class ChatRoomUserResponseDTO {
    @Schema(description = "채팅방 번호", example = "1")
    private Long id;
    @Schema(description = "채팅방 이름", example = "수어 학습 모임")
    private String chatRoomName;
    @Schema(description = "채팅방 타입", example = "그룹")
    private String chatRoomType;
    @Schema(description = "채팅방 생성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime chatRoomCreateAt;
    @Schema(description = "채팅방 프로필 이미지", example = "default.jpg")
    private String chatRoomProfile;
    @Schema(description = "채팅방 상세 소개", example = "수어 관련 학습을 공유하는 채팅방 입니다")
    private String chatRoomDetail;
    @Schema(description = "채팅방 채팅 가능 정원", example = "100")
    private int ChatRoomLimit;
    @Schema(description = "채팅방 현재 채팅중인 인원", example = "50")
    private int ChatRoomUsers;
    @Schema(description = "사용자가 채팅 참여 한 시각", example = "2024-01-01T00:00:00")
    private String chatStartAt;
    @Schema(description = "사용자가 채팅 마지막으로 읽은 시각", example = "2024-01-01T00:00:00")
    private String chatLastReadAt;

    public static  ChatRoomUserResponseDTO from(ChatRoomDTO chatRoomDTO){
        ChatRoomUserResponseDTO chatRoomUserResponseDTO = new ChatRoomUserResponseDTO();

        chatRoomUserResponseDTO.setId(chatRoomDTO.getId());
        chatRoomUserResponseDTO.setChatRoomName(chatRoomDTO.getChatRoomName());
        chatRoomUserResponseDTO.setChatRoomType(chatRoomDTO.getChatRoomType());
        chatRoomUserResponseDTO.setChatRoomCreateAt(chatRoomDTO.getChatRoomCreateAt());
        chatRoomUserResponseDTO.setChatRoomProfile(chatRoomDTO.getChatRoomProfile());
        chatRoomUserResponseDTO.setChatRoomDetail(chatRoomDTO.getChatRoomDetail());
        chatRoomUserResponseDTO.setChatRoomLimit(chatRoomDTO.getChatRoomLimit());
        chatRoomUserResponseDTO.setChatRoomUsers(chatRoomDTO.getChatRoomUsers());
        chatRoomUserResponseDTO.setChatStartAt(chatRoomDTO.getChatStartAt());

        return chatRoomUserResponseDTO;
    }
}
