package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.ChatResponseDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import com.app.springapp.domain.vo.ChatRoomVO;
import com.app.springapp.domain.vo.ChatVO;
import com.app.springapp.exception.ChatException;
import com.app.springapp.repository.ChatDAO;
import com.app.springapp.repository.ChatRoomDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class, ChatException.class})
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatDAO chatDAO;
    private final ChatRoomDAO chatRoomDAO;
    private final ChatRoomService chatRoomService;
    private final CommunityAuthService communityAuthService;


    //    채팅 메세지 관련
    //    채팅방 내 모든 메세지 불러오기
    @Override
    public List<ChatResponseDTO> loadAllChatRoomMessage(Long chatRoomId) {
        return chatDAO.findAll(chatRoomId).stream()
                .map(ChatResponseDTO::from)
                .collect(Collectors.toList());
    }

//    메세지 작성
    @Override
    public ApiResponseDTO writeChatMessage(Long chatRoomId, ChatRequestDTO chatRequestDTO) {
//        DTO 를 VO 로 변환 한 뒤에 작성 해야함

        boolean isJoined = this.isUserInChatRoom(chatRoomId);
        ChatVO chatVO = ChatVO.from(chatRequestDTO);
        chatVO.setChatRoomId(chatRoomId);
        chatVO.setUserId(communityAuthService.getUserId());

        try {
            if(!isJoined) {
                chatRoomService.joinChatRoom(chatRoomId);
            }
            chatDAO.save(chatVO);
            return ApiResponseDTO.of(true, "메세지 전송 성공");
        } catch (Exception e) {
            return ApiResponseDTO.of(false, "메세지 전송 실패");
        }
    }

//    유저가 해당 채팅방에 참여가 되어 있는지?
    @Override
    public boolean isUserInChatRoom(Long chatRoomId) {
        ChatVO chatVO = new ChatVO();
        chatVO.setChatRoomId(chatRoomId);
        chatVO.setUserId(communityAuthService.getUserId());
        log.info("테스트 위한 chat vo: {}", chatVO);
        return chatDAO.existByChatRoomIdAndUserId(chatVO) != 0;
    }

//    채팅방 관련

//    모든 채팅방 불러와주기 (커뮤니티 페이지 들어왔을 때 보이는 모든 채팅방)
    @Override
    public List<ChatRoomResponseDTO> loadAllChatRoom() {
        List<ChatRoomVO> chatRoomList = chatRoomDAO.findAll();
        return chatRoomList.stream()
                .map(ChatRoomResponseDTO::from)
                .collect(Collectors.toList());
    }
}
