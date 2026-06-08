package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.ChatRoomReportRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.ChatRoomReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/chat-room-reports")
@RequiredArgsConstructor
public class PrivateChatRoomReportApi {
    private final ChatRoomReportService chatRoomReportService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> post(
            @RequestBody ChatRoomReportRequestDTO chatRoomReportRequestDTO,
            Authentication authentication
    ) {
        Long userId = ((UserDTO) authentication.getPrincipal()).getId();
        chatRoomReportService.reportChatRoom(userId, chatRoomReportRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "채팅방 신고 성공"));
    }
}
