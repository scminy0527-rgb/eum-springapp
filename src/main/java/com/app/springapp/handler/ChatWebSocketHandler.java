package com.app.springapp.handler;

import com.app.springapp.domain.dto.ChatDTO;
import com.app.springapp.domain.dto.request.ChatRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.vo.ChatVO;
import com.app.springapp.service.ChatService;
import com.app.springapp.service.CommunityAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private final ObjectMapper objectMapper;

    // chatRoomId → 해당 방에 연결된 세션 목록
    private final Map<Long, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();
    private final CommunityAuthService communityAuthService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long chatRoomId = extractChatRoomId(session);
        roomSessions.computeIfAbsent(chatRoomId, k -> ConcurrentHashMap.newKeySet()).add(session);
        log.info("WebSocket 연결 - 채팅방: {}, 세션: {}", chatRoomId, session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long chatRoomId = extractChatRoomId(session);
        Long userId = communityAuthService.getUserId();
        communityAuthService.checkUserValidity(userId);
        ChatRequestDTO chatRequestDTO = objectMapper.readValue(message.getPayload(), ChatRequestDTO.class);

        log.info("사용자가 메세지 전송 시도");
        log.info("메세지: {}", chatRequestDTO);

        // DB 저장 — 내부적으로 CommunityAuthServiceImpl.getUserId() = 2L(더미) 사용
//        Long id = chatService.writeChatMessage(chatRoomId, chatRequestDTO);

//        DB 에서 다시 해당 채팅 아이디에 해당 하는 메세지 불러와서 반환 하는거 생각하기
//        ChatDTO chatDTO = chatService.loadChatMessageById(id);

//        메세지 작성 및 작성한 메세지 쿼리로 불러오는 트랜젝션
        ChatDTO chatDTO = chatService.playRealTimeChat(chatRoomId, chatRequestDTO);

        // 브로드캐스트 메시지 구성
        Map<String, Object> broadcastData = new HashMap<>();
//        채팅 메세지의 id 반환
        broadcastData.put("id", chatDTO.getId());
        broadcastData.put("chatContent", chatDTO.getChatContent());
        broadcastData.put("chatCreateAt", chatDTO.getChatCreateAt());
        broadcastData.put("chatType", chatDTO.getChatType());
        broadcastData.put("userNickname", chatDTO.getUserNickname());
        broadcastData.put("userProfile", chatDTO.getUserProfile());
        broadcastData.put("chatRoomId", chatRoomId);

        log.info("타 사용자 한테도 전달");

        broadcast(chatRoomId, objectMapper.writeValueAsString(broadcastData));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long chatRoomId = extractChatRoomId(session);
        Set<WebSocketSession> sessions = roomSessions.get(chatRoomId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                roomSessions.remove(chatRoomId);
            }
        }
        log.info("WebSocket 종료 - 채팅방: {}, 세션: {}", chatRoomId, session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket 오류 - 세션: {}, 에러: {}", session.getId(), exception.getMessage());
        if (session.isOpen()) {
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    private void broadcast(Long chatRoomId, String json) {
        Set<WebSocketSession> sessions = roomSessions.get(chatRoomId);
        if (sessions == null) return;
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                try {
                    s.sendMessage(new TextMessage(json));
                } catch (IOException e) {
                    log.error("브로드캐스트 실패 - 세션: {}", s.getId(), e);
                }
            }
        }
    }

    // URI 예시: /ws/chat/3 → 마지막 세그먼트가 chatRoomId
    private Long extractChatRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }
}