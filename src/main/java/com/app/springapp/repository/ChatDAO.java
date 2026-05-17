package com.app.springapp.repository;

import com.app.springapp.domain.dto.ChatDTO;
import com.app.springapp.domain.vo.ChatVO;
import com.app.springapp.mapper.ChatMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatDAO {
    private final ChatMapper chatMapper;

//    특정 채팅방 에서의 전체 메세지 불러오기
    public List<ChatDTO> findAll(ChatVO chatVO) {
        return chatMapper.selectAll(chatVO);
    }

//    웹소캣 용 채팅 메세지 가져오기
    public ChatDTO findById(Long id) {
        return chatMapper.selectById(id);
    }

//    유저가 해당 채팅방에 메시지 남긴 이력 확인
//    없다면 첫 메세지 남길 때 채팅방 참여 항목 갱신
    public int existByChatRoomIdAndUserId(ChatVO chatVO) {
        return chatMapper.existByChatRoomIdAndUserId(chatVO);
    }

//    채팅방 내 메세지 작성
    public void save(ChatVO chatVO) {
        chatMapper.insert(chatVO);
    }
}
