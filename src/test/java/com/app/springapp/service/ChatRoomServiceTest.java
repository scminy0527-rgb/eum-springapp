package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatRoomRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ChatRoomServiceTest {
    @Autowired
    private ChatRoomService chatRoomService;

//    채팅방에 참여 테스트
    @Test
    public void joinChatRoomTest() {
        Long chatRoomId = 3L;
        chatRoomService.joinChatRoom(chatRoomId);
    }

//    채팅방 생성 테스트
    @Test
    public void createChatRoomTest() {
        ChatRoomRequestDTO chatRoomRequestDTO = new ChatRoomRequestDTO();
        chatRoomRequestDTO.setChatRoomName("채팅방 이름입니다.");
        chatRoomRequestDTO.setChatRoomType("그룹");
        chatRoomRequestDTO.setChatRoomProfile("default.jpg");

        chatRoomService.createChatRoom(chatRoomRequestDTO);
    }
}
