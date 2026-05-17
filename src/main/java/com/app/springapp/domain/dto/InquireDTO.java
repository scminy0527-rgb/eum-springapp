package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "문의 DTO")
public class InquireDTO {
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
}