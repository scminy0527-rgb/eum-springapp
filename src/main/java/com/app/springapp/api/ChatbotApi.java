package com.app.springapp.api;

import com.app.springapp.domain.dto.request.ChatbotRequestDTO;
import com.app.springapp.domain.dto.response.ChatbotResponseDTO;
import com.app.springapp.service.ChatbotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatbotApi {

    private final ChatbotService chatbotService;

    @PostMapping("/chatbot")
    @Operation(summary = "챗봇 답변 요청", description = "사용자 메시지와 카테고리를 받아 AI 챗봇 답변을 반환합니다.")
    @ApiResponse(responseCode = "200", description = "답변 반환 성공")
    public ResponseEntity<ChatbotResponseDTO> chatbot(@RequestBody ChatbotRequestDTO request) {
        return ResponseEntity.ok(chatbotService.getReply(request));
    }
}