package com.app.springapp.service;

import com.app.springapp.domain.dto.ChatUserDTO;
import com.app.springapp.domain.dto.request.ChatRoomRequestDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import com.app.springapp.domain.dto.response.ChatUserResponseDTO;
import com.app.springapp.domain.vo.ChatUserVO;
import com.app.springapp.domain.vo.ChatRoomVO;
import com.app.springapp.exception.ChatException;
import com.app.springapp.repository.ChatUserDAO;
import com.app.springapp.repository.ChatRoomDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatUserDAO chatMemberDAO;
    private final ChatRoomDAO chatRoomDAO;
    private final CommunityAuthService communityAuthService;

    //    채팅방 방 생성
    @Override
    public void createChatRoom(ChatRoomRequestDTO chatRoomRequestDTO) {
        Long userId = communityAuthService.getUserId();
        if(userId == null || userId == 0L){
            throw new ChatException(HttpStatus.UNAUTHORIZED, "채팅방 생성 권한이 없습니다.");
        }
        ChatRoomVO chatRoomVO = ChatRoomVO.from(chatRoomRequestDTO);
        chatRoomVO.setUserId(userId);

        chatRoomDAO.save(chatRoomVO);
    }

//    유저가 채팅방에 참여
    @Override
    public void joinChatRoom(Long chatRoomId) {
        Long userId = communityAuthService.getUserId();
        ChatUserVO chatMemberVO = new ChatUserVO();
        chatMemberVO.setChatRoomId(chatRoomId);
        chatMemberVO.setUserId(userId);

        chatMemberDAO.save(chatMemberVO);
    }

//    채팅방 정보 불러오기
    @Override
    public ChatRoomResponseDTO getChatRoomInfo(Long id) {
        return chatRoomDAO.findById(id)
                .map(ChatRoomResponseDTO::from)
                .orElseThrow(() -> {
                    throw new ChatException(HttpStatus.BAD_REQUEST, "해당 채팅방을 불러올 수 없습니다");
                });
    }

    //    채팅방 내 채팅중인 인원 불러오기
    @Override
    public List<ChatUserResponseDTO> getChatRoomUsers(Long chatRoomId) {
        List<ChatUserDTO> chatUsers = chatMemberDAO.findByChatRoomId(chatRoomId);
        return chatUsers.stream()
                .map(ChatUserResponseDTO::from)
                .collect(Collectors.toList());
    }

//    사용자가 참여 중인 채팅방 페이지네이션 조회
    @Override
    public List<ChatRoomResponseDTO> getJoinedChatRooms(Map<String, Object> filters) {
        return chatRoomDAO.findByUserId(filters)
                .stream()
                .map(ChatRoomResponseDTO::from)
                .collect(Collectors.toList());
    }
}
