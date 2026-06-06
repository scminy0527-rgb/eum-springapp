package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.ChatRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.ChatResponseDTO;
import com.app.springapp.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/chats")
public class PrivateChatApi {

    private final ChatService chatService;

    @GetMapping("{chatRoomId}")
    @Operation(summary = "채팅 메세지 조회", description = "채팅방 내 전체 메세지 조회 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "전체 메세지 조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "chatRoomId",
            description = "채팅방 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> loadAllChatRoomMessage(
            @PathVariable Long chatRoomId,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        List<ChatResponseDTO> chats = chatService.loadAllChatRoomMessage(chatRoomId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "메세지 불러오기 성공", chats));
    }

    @PostMapping("{chatRoomId}")
    @Operation(summary = "채팅 메세지 작성", description = "채팅방 내 메세지 작성 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "메세지 작성 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "chatRoomId",
            description = "채팅방 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> writeChatRoomMessage(
            @PathVariable Long chatRoomId,
            @RequestBody ChatRequestDTO chatRequestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        chatService.writeChatMessage(chatRoomId, userId, chatRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "메세지 작성 성공"));
    }
}
