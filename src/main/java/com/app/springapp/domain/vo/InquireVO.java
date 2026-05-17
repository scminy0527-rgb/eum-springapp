package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.InquireDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class InquireVO {
    private Long id;
    private String inquireType;
    private String inquireEmail;
    private String inquireTitle;
    private String inquireContent;
    private String inquireFileUrl;
    private LocalDateTime inquireCreateAt;
    private String inquireStatus;
    private Long userId;
    private String inquireAnswer;

    public static InquireVO from(InquireDTO dto) {
        InquireVO inquireVO = new InquireVO();
        inquireVO.setId(dto.getId());
        inquireVO.setInquireType(dto.getInquireType());
        inquireVO.setInquireEmail(dto.getInquireEmail());
        inquireVO.setInquireTitle(dto.getInquireTitle());
        inquireVO.setInquireContent(dto.getInquireContent());
        inquireVO.setInquireAnswer(dto.getInquireAnswer());  // ← 추가
        inquireVO.setInquireFileUrl(dto.getInquireFileUrl());
        inquireVO.setInquireStatus(dto.getInquireStatus());
        inquireVO.setUserId(dto.getUserId());
        return inquireVO;
    }
}