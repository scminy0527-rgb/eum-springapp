package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatRoomReportRequestDTO;
import com.app.springapp.domain.vo.ChatRoomReportVO;
import com.app.springapp.repository.ChatRoomReportDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ChatRoomReportServiceImpl implements ChatRoomReportService {
    private final ChatRoomReportDAO chatRoomReportDAO;

    @Override
    public void reportChatRoom(Long userId, ChatRoomReportRequestDTO chatRoomReportRequestDTO) {
        ChatRoomReportVO chatRoomReportVO = ChatRoomReportVO.from(chatRoomReportRequestDTO);
        chatRoomReportVO.setUserId(userId);
        chatRoomReportDAO.save(chatRoomReportVO);
    }
}
