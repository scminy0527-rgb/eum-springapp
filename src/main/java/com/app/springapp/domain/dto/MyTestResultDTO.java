package com.app.springapp.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class MyTestResultDTO {
    private Long testApplyId;
    private String testTitle;
    private LocalDateTime testDate;
    private Integer testResultPoint; // null이면 결과 미발표
}
