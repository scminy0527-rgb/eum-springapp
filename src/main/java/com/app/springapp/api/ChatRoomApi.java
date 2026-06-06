package com.app.springapp.api;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
public class ChatRoomApi {

    private final ChatRoomService chatRoomService;

    //    채팅방 생성
    @PostMapping("")
    @Operation(summary = "채팅방 생성", description = "채팅방 생성 작성")
    @ApiResponse(responseCode = "201", description = "채팅방 생성 성공")
    @ApiResponse(responseCode = "401", description = "채팅방 생성 권한이 없습니다.")
    public ResponseEntity<ApiResponseDTO> createChatRoom(
            @RequestBody ChatRoomRequestDTO chatRoomRequestDTO
    ){
        Long id = chatRoomService.createChatRoom(chatRoomRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of(true, "채팅방 생성 성공", id));
    }

//    최초 1회 채팅방 참여중인 회원 불러오기
    @GetMapping("/{chatRoomId}/users")
    @Operation(summary = "채팅방 내 유저 불러오기", description = "채팅방 내 참여중인 유저 목록 불러오기")
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
            @PathVariable Long chatRoomId
    ){
        List<ChatUserResponseDTO> chatUsers = chatRoomService.getChatRoomUsers(chatRoomId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "채팅 참여 유저 불러오기 성공", chatUsers));
    }

//    현재 내가 채팅중인 채팅방 불러오기
    @GetMapping("/joined")
    @Operation(summary = "사용자가 진행중 채팅방", description = "사용자가 진행중인 채팅방 불러오기")
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
            @RequestParam(defaultValue = "1") int page
    ){
        Map<String, Object> result = new HashMap<>();
        result = chatRoomService.getJoinedChatRooms(page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "사용자 참여 채팅방 로드 성공", result));
    }

//    채팅방 내용 수정
    @PutMapping("/{id}")
    @Operation(summary = "채팅방 수정", description = "채팅방 관리자가 채팅방 정보를 수정 가능")
    public ResponseEntity<ApiResponseDTO> updateChatRoomInfo(
            @PathVariable Long id,
            @RequestBody ChatRoomRequestDTO chatRoomRequestDTO
    ){
        chatRoomService.updateChatRoomInfo(id, chatRoomRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "채팅방 정보 수정 성공"));
    }

//    채팅방 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "채팅방 삭제", description = "채팅방 관리자가 채팅방 삭제 하는 서비스")
    public ResponseEntity<ApiResponseDTO> deleteChatRoom(
            @PathVariable Long id
    ){
        chatRoomService.softDeleteChatRoom(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.of(true, "삭제 성공"));
    }
}
