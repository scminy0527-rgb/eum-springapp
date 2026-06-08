package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.request.ChatRoomReportRequestDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class ChatRoomReportVO {
    private Long id;
    private String chatRoomReportTitle;
    private String chatRoomReportDetail;
    private LocalDateTime chatRoomReportCreateAt;
    private Long userId;
    private Long chatRoomId;

    public static ChatRoomReportVO from(ChatRoomReportRequestDTO chatRoomReportRequestDTO) {
        ChatRoomReportVO chatRoomReportVO = new ChatRoomReportVO();
        chatRoomReportVO.setChatRoomReportTitle(chatRoomReportRequestDTO.getChatRoomReportTitle());
        chatRoomReportVO.setChatRoomReportDetail(chatRoomReportRequestDTO.getChatRoomReportDetail());
        chatRoomReportVO.setChatRoomId(chatRoomReportRequestDTO.getChatRoomId());

        return chatRoomReportVO;
    }
}
