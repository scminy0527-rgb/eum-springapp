package com.app.springapp.mapper;

import com.app.springapp.domain.dto.ChatUserDTO;
import com.app.springapp.domain.vo.ChatUserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatUserMapper {
//    채팅방 참여 내역 추가
    public void insert(ChatUserVO chatMemberVO);

//    채팅방에 참여 중인 유저 반환
    public List<ChatUserDTO> selectByChatRoomId(Long chatRoomId);

//    사용자가 참여 중인 채팅방 갯수
    public int countByUserId(Long userId);

//    사용자가 해당 체팅방 참여 했는지 여부 (채팅방 / 유저 매칭 개념)
    public int existByUserIdAndChatRoomId(ChatUserVO chatUserVO);
}
