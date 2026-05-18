package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatRoomRequestDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
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
        ChatRoomResponseDTO chatRoomInfo = chatRoomService.getChatRoomInfo(chatRoomId);
        log.info(chatRoomInfo.toString());

        chatRoomId = 100L;
        log.info("시나리오2: 존재하지 않는 방 id = {}", chatRoomId);
        chatRoomInfo = chatRoomService.getChatRoomInfo(chatRoomId);
        log.info(chatRoomInfo.toString());
    }
}
