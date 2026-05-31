package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatbotRequestDTO;
import com.app.springapp.domain.dto.response.ChatbotResponseDTO;

public interface ChatbotService {
    ChatbotResponseDTO getReply(ChatbotRequestDTO request);
}