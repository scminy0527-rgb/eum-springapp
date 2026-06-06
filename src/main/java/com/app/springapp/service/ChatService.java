package com.app.springapp.service;

import com.app.springapp.domain.dto.ChatDTO;
import com.app.springapp.domain.dto.request.ChatRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.ChatResponseDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import com.app.springapp.domain.vo.ChatRoomVO;

import java.util.List;
import java.util.Map;

public interface ChatService {
//    채팅방 내의 모든 메세지 불러오기
    public List<ChatResponseDTO> loadAllChatRoomMessage(Long chatRoomId, Long userId);

//    특정 채팅 메세지 가져오기
    public ChatDTO loadChatMessageById(Long id);

//    웹소켓에 의해 메세지 전송
    public ChatDTO playRealTimeChat(Long chatRoomId, ChatRequestDTO chatRequestDTO);

//    채팅방 내 메세지 작성
    public Long writeChatMessage(Long chatRoomId, Long userId, ChatRequestDTO chatRequestDTO);

//    유저가 채팅방 남긴 이력 확인 (해당 방에 참여가 되어 있는지 , 메세지 기준으로)
    public boolean isUserInChatRoom(Long chatRoomId, Long userId);

//    모든 채팅방들을 불러와주기 (페이지네이션)
    public Map<String, Object> loadAllChatRoom(Map<String, Object> req);
}
