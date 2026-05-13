package com.app.springapp.repository;

import com.app.springapp.domain.vo.ChatUserVO;
import com.app.springapp.mapper.ChatUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatUserDAO {
    private final ChatUserMapper chatMemberMapper;

//    유저의 채팅방 참여 현황 추가 (id 채팅방에 userId 유저 참여 기록 추가)
    public void save(ChatUserVO chatMemberVO){
        chatMemberMapper.insert(chatMemberVO);
    }
}
