package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import com.app.springapp.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/chat-rooms")
public class PrivateChatRoomApi {

    private final ChatRoomService chatRoomService;

    //    채팅방 정보 불러오기
    @GetMapping("/{chatRoomId}")
    @Operation(summary = "채팅방 정보", description = "채팅방 정보 불러오기")
    @ApiResponse(responseCode = "200", description = "채팅방 정보 불러오기 성공")
    @ApiResponse(responseCode = "400", description = "채팅방 정보 불러오기 실패")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "chatRoomId",
            description = "채팅방 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getPrivateChatRooms(
            @PathVariable Long chatRoomId,
            Authentication authentication
    ){
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        ChatRoomResponseDTO chatRoomInfo = chatRoomService.getChatRoomInfo(chatRoomId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "채팅방 정보 불러오기 성공", chatRoomInfo));
    }
}
