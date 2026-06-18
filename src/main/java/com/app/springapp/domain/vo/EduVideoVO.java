package com.app.springapp.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class EduVideoVO {
    private Long id;
    private String eduVideoTitle;
    private String eduVideoDetail;
    private String eduVideoType;
    private String eduVideoUrl;
    private String eduVideoThumbnailUrl;
    private LocalDateTime eduVideoCreateAt;
}
