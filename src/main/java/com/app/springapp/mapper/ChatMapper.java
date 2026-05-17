package com.app.springapp.mapper;

import com.app.springapp.domain.dto.ChatDTO;
import com.app.springapp.domain.vo.ChatVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
//    특정 채팅방 에서의 전체 메세지 불러오기
    public List<ChatDTO> selectAll(ChatVO  chatVO);

//    특정 메세지 하나 가져오기 (챗 웹소캣 핸들러 용)
    public ChatDTO selectById(Long id);

//    유저 채팅방 참여 현황에 대해서도 기록 해야 함
//    유저가 해당 방에 최초로 채팅을 남기면 ChatMember 테이블에 쓰기
    public int existByChatRoomIdAndUserId(ChatVO chatVO);

//    채팅방 내 메세지 작성
    public void insert(ChatVO chatVO);
}
