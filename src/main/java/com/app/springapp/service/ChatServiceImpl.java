package com.app.springapp.service;

import com.app.springapp.domain.dto.ChatDTO;
import com.app.springapp.domain.dto.ChatRoomDTO;
import com.app.springapp.domain.dto.request.ChatRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.ChatResponseDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import com.app.springapp.domain.vo.ChatRoomVO;
import com.app.springapp.domain.vo.ChatUserVO;
import com.app.springapp.domain.vo.ChatVO;
import com.app.springapp.exception.ChatException;
import com.app.springapp.repository.ChatDAO;
import com.app.springapp.repository.ChatRoomDAO;
import com.app.springapp.repository.ChatUserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class, ChatException.class})
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatDAO chatDAO;
    private final ChatRoomDAO chatRoomDAO;
    private final ChatRoomService chatRoomService;
//    private final CommunityAuthService communityAuthService;
    private final ChatUserDAO chatUserDAO;
    private final CommunityAuthService communityAuthService;

    //    채팅 메세지 관련
    //    채팅방 내 모든 메세지 불러오기
    @Override
    public List<ChatResponseDTO> loadAllChatRoomMessage(Long chatRoomId, Long userId) {
        ChatVO chatVO = new ChatVO();
        chatVO.setChatRoomId(chatRoomId);
        chatVO.setUserId(userId);

        return chatDAO.findAll(chatVO).stream()
                .map(ChatResponseDTO::from)
                .collect(Collectors.toList());
    }

//    특정 채팅 메세지 가져오기
    @Override
    public ChatDTO loadChatMessageById(Long id) {
        return chatDAO.findById(id);
    }

//    웹소캣 통해서 메세지 작성 및 작성 된 메세지 반환
    @Override
    public ChatDTO playRealTimeChat(Long chatRoomId, ChatRequestDTO chatRequestDTO) {
        Long userId = communityAuthService.getUserId();
        Long id = this.writeChatMessage(chatRoomId, userId, chatRequestDTO);
        return chatDAO.findById(id);
    }

    //    메세지 작성
    @Override
    public Long writeChatMessage(Long chatRoomId, Long userId, ChatRequestDTO chatRequestDTO) {
        boolean isJoined = this.isUserInChatRoom(chatRoomId, userId);
        Long id = 0L;
        ChatVO chatVO = ChatVO.from(chatRequestDTO);
        chatVO.setChatRoomId(chatRoomId);
        chatVO.setUserId(userId);

        try {
            if(!isJoined) {
                chatRoomService.joinChatRoom(chatRoomId);
            }
            chatDAO.save(chatVO);
            id = chatVO.getId();
            return id;
        } catch (Exception e) {
            throw new ChatException(HttpStatus.BAD_REQUEST, "메세지 전송 실패");
        }
    }

//    유저가 해당 채팅방에 참여가 되어 있는지?
    @Override
    public boolean isUserInChatRoom(Long chatRoomId, Long userId) {
        ChatUserVO chatUserVO = new ChatUserVO();
        chatUserVO.setChatRoomId(chatRoomId);
        chatUserVO.setUserId(userId);

        return chatUserDAO.existByUserIdAndChatRoomId(chatUserVO) != 0;
    }

//    채팅방 관련

//    모든 채팅방 불러와주기 (커뮤니티 페이지 들어왔을 때 보이는 모든 채팅방, 페이지네이션)
    @Override
    public Map<String, Object> loadAllChatRoom(Map<String, Object> req) {
        int page = (Integer) req.get("page");
        int size = (Integer) req.get("size");
        String keyword = (String) req.get("keyword");

        int offset = (page - 1) * size;

        Map<String, Object> filters = new HashMap<>();
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("keyword", keyword);

        List<ChatRoomResponseDTO> rooms = chatRoomDAO.findAllWithPaging(filters).stream()
                .map(ChatRoomResponseDTO::from)
                .collect(Collectors.toList());

        int roomCounts = chatRoomDAO.findCount();

        Map<String, Object> result = new HashMap<>();
        result.put("rooms", rooms);
        result.put("currentPage", page);
        result.put("totalPages", (int) Math.ceil((double) roomCounts / size));
        result.put("size", size);
        result.put("roomCounts", roomCounts);

        return result;
    }
}
