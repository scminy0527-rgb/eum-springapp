package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatRoomRequestDTO;
import com.app.springapp.domain.vo.ChatUserVO;
import com.app.springapp.domain.vo.ChatRoomVO;
import com.app.springapp.exception.ChatException;
import com.app.springapp.repository.ChatUserDAO;
import com.app.springapp.repository.ChatRoomDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//        Long userId = 0L;
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
}
