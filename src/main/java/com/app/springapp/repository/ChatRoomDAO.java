package com.app.springapp.repository;

import com.app.springapp.domain.vo.ChatRoomVO;
import com.app.springapp.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomDAO {

    private final ChatRoomMapper chatRoomMapper;

//    채팅방 목록 전체 불러와주기
    public List<ChatRoomVO> findAll(){
        return chatRoomMapper.selectAll();
    }

//    채팅방 생성
    public void save(ChatRoomVO chatRoomVO){
        chatRoomMapper.insert(chatRoomVO);
    }
}
