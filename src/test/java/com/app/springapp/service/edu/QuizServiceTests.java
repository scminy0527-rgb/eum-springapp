package com.app.springapp.service.edu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class QuizServiceTests {
    @Autowired
    private QuizService quizService;

    @Test
    public void getQuizListTest() {
        quizService.getQuizList().forEach(quiz -> log.info("quiz: {}", quiz));
    }

    @Test
    public void getQuizByIdTest() {
        log.info("quiz: {}", quizService.getQuizById(1L));
    }

}
