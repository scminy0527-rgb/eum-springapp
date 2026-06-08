package com.app.springapp.repository;

import com.app.springapp.domain.vo.ChatRoomReportVO;
import com.app.springapp.mapper.ChatRoomReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomReportDAO {
    private final ChatRoomReportMapper chatRoomReportMapper;

    public void save(ChatRoomReportVO chatRoomReportVO) {
        chatRoomReportMapper.insert(chatRoomReportVO);
    }
}
