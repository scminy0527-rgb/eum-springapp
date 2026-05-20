package com.app.springapp.service.edu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class QuizQuestionServiceTests {
    @Autowired
    private QuizQuestionService quizQuestionService;

    @Test
    public void getQuestionsByQuizIdTest() {
        quizQuestionService.getQuestionsByQuizId(1L).forEach(question -> log.info("question: {}", question));
    }

    @Test
    public void getQuestionByIdTest() {
        log.info("question: {}", quizQuestionService.getQuestionById(1L));
    }
}
