package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.NoticeDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class NoticeVO {
    private Long id;
    private String noticeTitle;
    private String noticeContent;
    private String noticeCategory;
    private int noticePinned;
    private String noticeFileUrl;
    private LocalDateTime noticeCreateAt;
    private Long userId;



    public static NoticeVO from(NoticeDTO dto) {
        NoticeVO noticeVO = new NoticeVO();
        noticeVO.setId(dto.getId());
        noticeVO.setNoticeTitle(dto.getNoticeTitle());
        noticeVO.setNoticeContent(dto.getNoticeContent());
        noticeVO.setNoticeCategory(dto.getNoticeCategory());
        noticeVO.setNoticePinned(dto.getNoticePinned());
        noticeVO.setNoticeFileUrl(dto.getNoticeFileUrl());
        noticeVO.setUserId(dto.getUserId());
        return noticeVO;
    }
}
