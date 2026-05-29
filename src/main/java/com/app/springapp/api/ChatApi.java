package com.app.springapp.api;

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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatApi {

    private final ChatService chatService;

//    채팅방 내에서 메세지 전체 조회
    @GetMapping("{chatRoomId}")
    @Operation(summary = "채팅 메세지 조회", description = "채팅방 내 전체 메세지 조회")
    @ApiResponse(responseCode = "200", description = "전체 메세지 조회 성공")
    @ApiResponse(responseCode = "400", description = "전체 메세지 조회 실패 (잘못된 요청)")
    @Parameter(
            name = "chatRoomId",
            description = "채팅방 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> loadAllChatRoomMessage(
            @PathVariable Long chatRoomId
    ) {
        List<ChatResponseDTO> chats = chatService.loadAllChatRoomMessage(chatRoomId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "메세지 불러오기 성공", chats));
    }

//    채팅방에 메세지 작성
    @PostMapping("{chatRoomId}")
    @Operation(summary = "채팅 메세지 작성", description = "채팅방 내에서 메세지 작성")
    @ApiResponse(responseCode = "200", description = "메세지 작성 성공")
    @ApiResponse(responseCode = "400", description = "메세지 작성 실패 (잘못된 요청)")
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
            @RequestBody ChatRequestDTO chatRequestDTO
            ){
        chatService.writeChatMessage(chatRoomId, chatRequestDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "메세지 작성 성공"));
    }

//    모든 채팅방 불러오기 (페이지네이션)
    @Operation(summary = "모든 채팅방 불러오기", description = "모든 채팅방 불러오기")
    @ApiResponse(responseCode = "200", description = "채팅방 목록 불러오기 성공")
    @ApiResponse(responseCode = "400", description = "채팅방 목록 불러오기 실패 (잘못된 요청)")
    @Parameter(
            name = "page",
            description = "채팅방 목록 페이지 번호",
            example = "1",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "keyword",
            description = "채팅방 목록 검색 키워드",
            example = "수어",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "String")
    )
    @GetMapping("/rooms")
    public ResponseEntity<ApiResponseDTO> getAllChatRooms(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false, defaultValue = "") String keyword
    ){
        Map<String,Object> req = new HashMap<>();
        req.put("page", page);
        req.put("size", size);
        req.put("keyword", keyword);

        Map<String, Object> result = chatService.loadAllChatRoom(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "채팅방 불러오기 성공", result));
    }
}
