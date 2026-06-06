package com.app.springapp.service;

import com.app.springapp.domain.dto.ChatDTO;
import com.app.springapp.domain.dto.request.ChatRequestDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import com.app.springapp.repository.ChatDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class ChatServiceTest {
    @Autowired
    private ChatService chatService;

    // 채팅방 내 모든 메세지 불러오는 테스트
//    테스트: 예
    @Test
    public void loadAllChatRoomMessageTest() {
        chatService.loadAllChatRoomMessage(1L, 1L)
                .stream()
                .forEach((chat) -> log.info(chat.toString()));
    }

//    채팅 메세지 단일 조회 테스트
    @Test
    public void loadChatMessageByIdTest() {
        ChatDTO chatDTO = chatService.loadChatMessageById(1L);
        log.info(chatDTO.toString());
    }

//    해당 채팅방에 유저가 참여 중인지 확인
//    테스트 결과: 예
    @Test
    public void isUserInChatRoomTest(){
        Long chatRoomId = 3L;
        Long userId = 1L;

        boolean result = chatService.isUserInChatRoom(chatRoomId, userId);
        log.info("결과: {}", result);
    }

//    채팅방 내에 메세지 작성
//    테스트: 예
    @Test
    public void writeChatRoomMessageTest() {
        Long chatRoomId = 3L;
        Long userId = 1L;

        ChatRequestDTO chatRequestDTO = new ChatRequestDTO();
        chatRequestDTO.setChatContent("전 귀가 안들려요");
        chatRequestDTO.setChatType("텍스트");

        chatService.writeChatMessage(chatRoomId, userId, chatRequestDTO);
    }

//    모든 채팅방 보여주는 서비스
//    테스트: 예
    @Test
    public void loadAllChatRoomTest() {
        Map<String,Object> req = new HashMap<>();
        req.put("page", 1);
        req.put("size", 4);

        Map<String, Object> result = chatService.loadAllChatRoom(req);

        List<ChatRoomResponseDTO> rooms = (List<ChatRoomResponseDTO>) result.get("rooms");
        rooms
                .stream()
                .forEach((chatRoom) -> log.info(chatRoom.toString()));
    }
}
