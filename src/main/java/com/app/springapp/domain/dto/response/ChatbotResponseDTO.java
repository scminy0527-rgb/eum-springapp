package com.app.springapp.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatbotResponseDTO {
    private String reply;
    private String buttonLabel;
    private String buttonPath;
}