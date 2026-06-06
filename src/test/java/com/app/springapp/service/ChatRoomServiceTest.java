package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatRoomRequestDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

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

//    채팅방 참여 중인 유저 불러오기 테스트
    @Test
    public void getChatRoomUsersTest() {
        Long chatRoomId = 3L;
        chatRoomService.getChatRoomUsers(chatRoomId)
                .stream()
                .forEach((chatUser) -> {
                    log.info(chatUser.toString());
                });
    }

    @Test
    public void getChatRoomInfoTest() {
        Long chatRoomId = 3L;
        log.info("시나리오1: 존재하는 방 id = {}", chatRoomId);
        ChatRoomResponseDTO chatRoomInfo = chatRoomService.getChatRoomInfo(chatRoomId, 2L);
        log.info(chatRoomInfo.toString());

        chatRoomId = 100L;
        log.info("시나리오2: 존재하지 않는 방 id = {}", chatRoomId);
        chatRoomInfo = chatRoomService.getChatRoomInfo(chatRoomId, 2L);
        log.info(chatRoomInfo.toString());
    }

    @Test
    public void getJoinedChatRoomsTest() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("userId", 1L);
        filters.put("offset", 0);
        filters.put("size", 10);

        Map<String, Object> result = chatRoomService.getJoinedChatRooms(1);
        log.info(result.toString());
    }

//    채팅방 정보 수정 테스트
    @Test
    public void updateChatRoomInfoTest() {
        ChatRoomRequestDTO chatRoomRequestDTO = new ChatRoomRequestDTO();
        chatRoomRequestDTO.setChatRoomName("부산 수어");
        chatRoomRequestDTO.setChatRoomDetail("모임입니다.");
        chatRoomRequestDTO.setChatRoomLimit(65);
        chatRoomRequestDTO.setChatRoomProfile("default.jpg");
        Long chatRoomId = 5L;

        chatRoomService.updateChatRoomInfo(chatRoomId, chatRoomRequestDTO);
    }

//    채팅방 소프트삭제 테스트
    @Test
    public void softDeleteChatRoomTest() {
        chatRoomService.softDeleteChatRoom(5L);
    }
}
