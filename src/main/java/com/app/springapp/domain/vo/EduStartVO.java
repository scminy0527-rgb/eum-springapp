package com.app.springapp.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class EduStartVO {
    private Long id;
    private LocalDateTime eduStartAt;
    private Long userId;
    private Long eduId;
    private int eduStartIsCompleted;
    private int eduStartTotalCount;
    private int eduStartCompletedCount;
    private int eduStartCorrectCount;
}
