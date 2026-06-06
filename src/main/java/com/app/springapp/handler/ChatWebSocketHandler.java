package com.app.springapp.handler;

import com.app.springapp.domain.dto.ChatDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.ChatRequestDTO;
import com.app.springapp.domain.enums.SocialProvider;
import com.app.springapp.repository.UserDAO;
import com.app.springapp.service.ChatService;
import com.app.springapp.service.CommunityAuthService;
import com.app.springapp.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    private final CommunityAuthService communityAuthService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDAO userDAO;

    // chatRoomId → 해당 방에 연결된 세션 목록
    private final Map<Long, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        UserDTO userDTO = extractUserFromHandshake(session);
        if (userDTO == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE);
            return;
        }
        session.getAttributes().put("user", userDTO);

        Long chatRoomId = extractChatRoomId(session);
        roomSessions.computeIfAbsent(chatRoomId, k -> ConcurrentHashMap.newKeySet()).add(session);
        log.info("WebSocket 연결 - 채팅방: {}, 세션: {}", chatRoomId, session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        UserDTO userDTO = (UserDTO) session.getAttributes().get("user");
        if (userDTO == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE);
            return;
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDTO, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);

        try {
            Long chatRoomId = extractChatRoomId(session);
            communityAuthService.checkUserValidity(communityAuthService.getUserId());

            ChatRequestDTO chatRequestDTO = objectMapper.readValue(message.getPayload(), ChatRequestDTO.class);
            ChatDTO chatDTO = chatService.playRealTimeChat(chatRoomId, chatRequestDTO);

            Map<String, Object> broadcastData = new HashMap<>();
            broadcastData.put("id", chatDTO.getId());
            broadcastData.put("chatContent", chatDTO.getChatContent());
            broadcastData.put("chatCreateAt", chatDTO.getChatCreateAt());
            broadcastData.put("chatType", chatDTO.getChatType());
            broadcastData.put("userNickname", chatDTO.getUserNickname());
            broadcastData.put("userProfile", chatDTO.getUserProfile());
            broadcastData.put("chatRoomId", chatRoomId);

            broadcast(chatRoomId, session, broadcastData);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private UserDTO extractUserFromHandshake(WebSocketSession session) {
        try {
            List<String> cookieHeaders = session.getHandshakeHeaders().get("cookie");
            if (cookieHeaders == null || cookieHeaders.isEmpty()) return null;

            String accessToken = null;
            for (String header : cookieHeaders) {
                for (String part : header.split(";")) {
                    String[] kv = part.trim().split("=", 2);
                    if (kv.length == 2 && "accessToken".equals(kv[0].trim())) {
                        accessToken = kv[1].trim();
                        break;
                    }
                }
                if (accessToken != null) break;
            }
            if (accessToken == null) return null;

            Claims claims = jwtTokenUtil.parseToken(accessToken);
            String userEmail = (String) claims.get("userEmail");
            String socialUserProvider = (String) claims.get("socialUserProvider");

            UserDTO userDTO = new UserDTO();
            userDTO.setUserEmail(userEmail);
            userDTO.setSocialUserProvider(SocialProvider.fromValue(socialUserProvider));

            return userDAO.findUserByUserEmailAndSocialUserProvider(userDTO).orElse(null);
        } catch (Exception e) {
            log.warn("WebSocket 인증 실패: {}", e.getMessage());
            return null;
        }
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

    private void broadcast(Long chatRoomId, WebSocketSession senderSession, Map<String, Object> data) {
        Set<WebSocketSession> sessions = roomSessions.get(chatRoomId);
        if (sessions == null) return;
        for (WebSocketSession s : sessions) {
            if (!s.isOpen()) continue;
            try {
                data.put("chatIsMe", s.getId().equals(senderSession.getId()));
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(data)));
            } catch (IOException e) {
                log.error("브로드캐스트 실패 - 세션: {}", s.getId(), e);
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