package com.app.springapp.repository;

import com.app.springapp.domain.dto.ChatRoomDTO;
import com.app.springapp.domain.vo.ChatRoomVO;
import com.app.springapp.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomDAO {

    private final ChatRoomMapper chatRoomMapper;

//    채팅방 목록 전체 불러오기
    public List<ChatRoomDTO> findAll(){
        return chatRoomMapper.select(null);
    }

//    채팅방 단건 정보 불러오기
    public Optional<ChatRoomDTO> findById(Map<String, Object> filter){
        return chatRoomMapper.select(filter).stream().findFirst();
    }

//    전체 채팅방 목록 페이지네이션 조회
    public List<ChatRoomDTO> findAllWithPaging(Map<String, Object> filters){
        return chatRoomMapper.selectAllWithPaging(filters);
    }

//    사용자가 현재 참여 중인 채팅방 목록 페이지네이션 조회
    public List<ChatRoomDTO> findByUserId(Map<String, Object> filters){
        return chatRoomMapper.selectByUserId(filters);
    }

//    채팅방 전체 개수 조회
    public int findCount(){
        return chatRoomMapper.selectCount();
    }

//    채팅방 생성
    public void save(ChatRoomVO chatRoomVO){
        chatRoomMapper.insert(chatRoomVO);
    }

//    채팅방 수정
    public void update(ChatRoomVO chatRoomVO){
        chatRoomMapper.update(chatRoomVO);
    }

//    채팅방 소프트 삭제
    public void updateChatRoomIsDeleteById(ChatRoomVO chatRoomVO){
        chatRoomMapper.updateChatRoomIsDeleteById(chatRoomVO);
    }

//    두 유저 간 1:1 채팅방 조회
    public Optional<Long> findDirectRoom(Long userId, Long targetUserId){
        return Optional.ofNullable(chatRoomMapper.selectDirectRoom(userId, targetUserId));
    }
}
