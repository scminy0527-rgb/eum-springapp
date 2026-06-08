package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatRoomReportRequestDTO;

public interface ChatRoomReportService {
    public void reportChatRoom(Long userId, ChatRoomReportRequestDTO chatRoomReportRequestDTO);
}
