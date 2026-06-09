package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.ChatRoomRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import com.app.springapp.domain.dto.response.ChatUserResponseDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/chat-rooms")
public class PrivateChatRoomApi {

    private final ChatRoomService chatRoomService;

    //    1:1 채팅방 조회 또는 생성
    @PostMapping("/direct")
    @Operation(summary = "1:1 채팅방 시작", description = "대상 유저와의 1:1 채팅방을 가져오거나 없으면 생성 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "1:1 채팅방 ID 반환")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ApiResponseDTO> getOrCreateDirectRoom(
            @RequestBody Map<String, Long> body,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();
        Long targetUserId = body.get("targetUserId");

        Long chatRoomId = chatRoomService.getOrCreateDirectRoom(userId, targetUserId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "1:1 채팅방 준비 완료", chatRoomId));
    }

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

    //    채팅방 생성
    @PostMapping("")
    @Operation(summary = "채팅방 생성", description = "채팅방 생성 작성 (로그인 필요)")
    @ApiResponse(responseCode = "201", description = "채팅방 생성 성공")
    @ApiResponse(responseCode = "401", description = "채팅방 생성 권한이 없습니다.")
    public ResponseEntity<ApiResponseDTO> createChatRoom(
            @RequestBody ChatRoomRequestDTO chatRoomRequestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        Long id = chatRoomService.createChatRoom(chatRoomRequestDTO, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of(true, "채팅방 생성 성공", id));
    }

    //    채팅방 내 참여중인 유저 불러오기
    @GetMapping("/{chatRoomId}/users")
    @Operation(summary = "채팅방 내 유저 불러오기", description = "채팅방 내 참여중인 유저 목록 불러오기 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "채팅방 내 참여중인 유저 목록 불러오기 성공")
    @ApiResponse(responseCode = "400", description = "채팅방 내 참여중인 유저 목록 불러오기 실패")
    @Parameter(
            name = "chatRoomId",
            description = "채팅방 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getChatRoomUsers(
            @PathVariable Long chatRoomId,
            Authentication authentication
    ) {
        List<ChatUserResponseDTO> chatUsers = chatRoomService.getChatRoomUsers(chatRoomId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "채팅 참여 유저 불러오기 성공", chatUsers));
    }

    //    현재 내가 채팅중인 채팅방 불러오기
    @GetMapping("/joined")
    @Operation(summary = "사용자가 진행중 채팅방", description = "사용자가 진행중인 채팅방 불러오기 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "사용자가 진행중인 채팅방 불러오기 성공")
    @ApiResponse(responseCode = "400", description = "사용자가 진행중인 채팅방 불러오기 실패")
    @Parameter(
            name = "page",
            description = "페이지 번호",
            example = "1",
            required = true,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getJoinedChatRooms(
            @RequestParam(defaultValue = "1") int page,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        Map<String, Object> result = chatRoomService.getJoinedChatRooms(page, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "사용자 참여 채팅방 로드 성공", result));
    }

    //    채팅방 내용 수정
    @PutMapping("/{id}")
    @Operation(summary = "채팅방 수정", description = "채팅방 관리자가 채팅방 정보를 수정 가능 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "채팅방 정보 수정 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ApiResponseDTO> updateChatRoomInfo(
            @PathVariable Long id,
            @RequestBody ChatRoomRequestDTO chatRoomRequestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        chatRoomService.updateChatRoomInfo(id, chatRoomRequestDTO, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "채팅방 정보 수정 성공"));
    }

    //    채팅방 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "채팅방 삭제", description = "채팅방 관리자가 채팅방 삭제 (로그인 필요)")
    @ApiResponse(responseCode = "204", description = "채팅방 삭제 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ApiResponseDTO> deleteChatRoom(
            @PathVariable Long id,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        chatRoomService.softDeleteChatRoom(id, userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.of(true, "삭제 성공"));
    }
}
