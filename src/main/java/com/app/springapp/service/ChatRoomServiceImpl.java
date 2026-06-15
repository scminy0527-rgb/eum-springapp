package com.app.springapp.service;

import com.app.springapp.domain.dto.ChatUserDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.ChatRoomRequestDTO;
import com.app.springapp.domain.dto.response.ChatRoomResponseDTO;
import com.app.springapp.domain.dto.response.ChatRoomUserResponseDTO;
import com.app.springapp.domain.dto.response.ChatUserResponseDTO;
import com.app.springapp.domain.vo.ChatUserVO;
import com.app.springapp.domain.vo.ChatRoomVO;
import com.app.springapp.exception.ChatException;
import com.app.springapp.repository.ChatUserDAO;
import com.app.springapp.repository.ChatRoomDAO;
import com.app.springapp.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatUserDAO chatMemberDAO;
    private final ChatRoomDAO chatRoomDAO;
    private final ChatUserDAO chatUserDAO;
    private final UserDAO userDAO;
//    private final CommunityAuthService communityAuthService;
    private final PostService postService;

    //    채팅방 방 생성 (빈 그룹 채팅방)
    @Override
    public Long createChatRoom(ChatRoomRequestDTO chatRoomRequestDTO, Long userId) {
        ChatRoomVO chatRoomVO = ChatRoomVO.from(chatRoomRequestDTO);
        chatRoomVO.setUserId(userId);

        chatRoomDAO.save(chatRoomVO);
        Long id = chatRoomVO.getId();

//        채팅방 참여 현황 추가 (방장이 자신이 만든 채팅방에 참여)
        ChatUserVO chatUserVO = new ChatUserVO();
        chatUserVO.setChatRoomId(id);
        chatUserVO.setUserId(userId);

        chatUserDAO.save(chatUserVO);

//        트랜젝션 완료 후 만들어진 방 반환
        return id;
    }

//    유저가 채팅방에 참여
    @Override
    public void joinChatRoom(Long chatRoomId, Long userId) {
        ChatUserVO chatMemberVO = new ChatUserVO();
        chatMemberVO.setChatRoomId(chatRoomId);
        chatMemberVO.setUserId(userId);

        chatMemberDAO.save(chatMemberVO);
    }

//    채팅방 정보 불러오기
    @Override
    public ChatRoomResponseDTO getChatRoomInfo(Long id, Long userId) {

        Map<String,Object> filter = new HashMap<>();
        filter.put("id", id);
        filter.put("userId", userId);

        return chatRoomDAO.findById(filter)
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
    public Map<String, Object> getJoinedChatRooms(int page, Long userId) {
        Map<String, Object> filters = new HashMap<>();
        int size = 10;
        int offset = (page - 1) * size;
        filters.put("offset", offset);
        filters.put("size", size);
        filters.put("userId", userId);

//        결과
        Map<String, Object> result = new HashMap<>();

        List<ChatRoomUserResponseDTO> rooms = chatRoomDAO.findByUserId(filters)
                .stream()
                .map(ChatRoomUserResponseDTO::from)
                .collect(Collectors.toList());
        int roomCounts = chatUserDAO.countByUserId(userId);

        result.put("rooms", rooms);
        result.put("currentPage", page);
        result.put("totalPages", postService.calcTotalPages(roomCounts, size));
        result.put("size", size);
        result.put("roomCounts", roomCounts);


        return result;
    }

//    채팅방 정보 수정
    @Override
    public void updateChatRoomInfo(Long id, ChatRoomRequestDTO chatRoomRequestDTO, Long userId) {
        ChatRoomVO chatRoomVO = ChatRoomVO.from(chatRoomRequestDTO);
        chatRoomVO.setId(id);
        chatRoomVO.setUserId(userId);
        chatRoomDAO.update(chatRoomVO);
    }


//    채팅방 삭제
    @Override
    public void softDeleteChatRoom(Long id, Long userId) {
        ChatRoomVO chatRoomVO = new ChatRoomVO();
        chatRoomVO.setId(id);
        chatRoomVO.setUserId(userId);
        chatRoomDAO.updateChatRoomIsDeleteById(chatRoomVO);
    }

//    1:1 채팅방 조회 또는 생성
    @Override
    public Long getOrCreateDirectRoom(Long userId, Long targetUserId) {
        return chatRoomDAO.findDirectRoom(userId, targetUserId).orElseGet(() -> {
            String myNickname = userDAO.findUserById(userId)
                    .map(UserDTO::getUserNickname).orElse("사용자");
            String targetNickname = userDAO.findUserById(targetUserId)
                    .map(UserDTO::getUserNickname).orElse("사용자");

            ChatRoomVO chatRoomVO = new ChatRoomVO();
            chatRoomVO.setChatRoomName(myNickname + " & " + targetNickname);
            chatRoomVO.setChatRoomType("개인");
            chatRoomVO.setChatRoomProfile("default.jpg");
            chatRoomVO.setChatRoomLimit(2);
            chatRoomVO.setUserId(userId);
            chatRoomDAO.save(chatRoomVO);
            Long chatRoomId = chatRoomVO.getId();

            ChatUserVO myUser = new ChatUserVO();
            myUser.setChatRoomId(chatRoomId);
            myUser.setUserId(userId);
            chatUserDAO.save(myUser);

            ChatUserVO targetUser = new ChatUserVO();
            targetUser.setChatRoomId(chatRoomId);
            targetUser.setUserId(targetUserId);
            chatUserDAO.save(targetUser);

            return chatRoomId;
        });
    }
}
