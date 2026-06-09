package com.app.springapp.mapper;

import com.app.springapp.domain.dto.ChatRoomDTO;
import com.app.springapp.domain.vo.ChatRoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChatRoomMapper {
//    두 유저 간 1:1 채팅방 조회
    public Long selectDirectRoom(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);
//    채팅방 목록 전체 또는 특정 채팅방 조회 (id null → 전체, id 값 있음 → 단건)
    public List<ChatRoomDTO> select(Map<String, Object> filter);

//    채팅방 목록 페이징 조회
    public List<ChatRoomDTO> selectAllWithPaging(Map<String, Object> filters);

//    채팅방 전체 개수 조회
    public int selectCount();

//    사용자가 현재 참여중인 채팅방 목록 페이지네이션 조회
    public List<ChatRoomDTO> selectByUserId(Map<String, Object> filters);

//    채팅방 생성
    public void insert(ChatRoomVO chatRoomVO);

//    채팅방 수정
    public void update(ChatRoomVO chatRoomVO);

//    채팅방 소프트 삭제
    public void updateChatRoomIsDeleteById(ChatRoomVO chatRoomVO);
}
