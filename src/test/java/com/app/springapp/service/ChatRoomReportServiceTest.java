package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatRoomReportRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ChatRoomReportServiceTest {
    @Autowired
    ChatRoomReportService chatRoomReportService;

    @Test
    public void reportChatRoomTest() {
        Long userId = 1L;
        ChatRoomReportRequestDTO chatRoomReportRequestDTO = new ChatRoomReportRequestDTO();
        chatRoomReportRequestDTO.setChatRoomReportTitle("test");
        chatRoomReportRequestDTO.setChatRoomReportDetail("test");
        chatRoomReportRequestDTO.setChatRoomId(1L);

        chatRoomReportService.reportChatRoom(userId, chatRoomReportRequestDTO);
    }
}
