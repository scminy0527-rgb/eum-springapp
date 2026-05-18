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
        NoticeVO vo = new NoticeVO();
        vo.setId(dto.getId());
        vo.setNoticeTitle(dto.getNoticeTitle());
        vo.setNoticeContent(dto.getNoticeContent());
        vo.setNoticeCategory(dto.getNoticeCategory());
        vo.setNoticePinned(dto.getNoticePinned());
        vo.setNoticeFileUrl(dto.getNoticeFileUrl());
        vo.setUserId(dto.getUserId());
        return vo;
    }
}
