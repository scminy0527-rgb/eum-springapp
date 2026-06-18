package com.app.springapp.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class QuizStartVO {
    private Long id;
    private LocalDateTime quizStartAt;
    private Long userId;
    private Long quizId;
    private int quizStartCompletedCount;
    private int quizStartCorrectCount;
}
