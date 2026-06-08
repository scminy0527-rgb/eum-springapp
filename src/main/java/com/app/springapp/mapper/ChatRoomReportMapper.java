package com.app.springapp.mapper;

import com.app.springapp.domain.vo.ChatRoomReportVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRoomReportMapper {
//    채팅방 신고
    public void insert(ChatRoomReportVO chatRoomReportVO);
}
