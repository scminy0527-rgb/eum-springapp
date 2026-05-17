package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "채팅 메시지 DTO")
public class ChatDTO {
    @Schema(description = "채팅 번호", example = "1")
    private Long id;
    @Schema(description = "채팅 내용", example = "안녕하세요!")
    private String chatContent;
    @Schema(description = "채팅 생성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime chatCreateAt;
    @Schema(description = "채팅 타입", example = "텍스트")
    private String chatType;
    @Schema(description = "유저 번호", example = "1")
    private Long userId;
    @Schema(description = "유저 닉네임", example = "수어러버박지민")
    private String userNickname;
    @Schema(description = "유저 프로필", example = "default.jpg")
    private String userProfile;
    @Schema(description = "채팅방 번호", example = "1")
    private Long chatRoomId;
    @Schema(description = "내가 작성한 여부", example = "true")
    private Boolean chatIsMe;
}
