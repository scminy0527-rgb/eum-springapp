package com.app.springapp.service.edu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class QuizChoiceServiceTests {
    @Autowired
    private QuizChoiceService quizChoiceService;

    @Test
    public void getChoicesByQuestionIdTest() {
        quizChoiceService.getChoicesByQuestionId(1L).forEach(choice -> log.info("choice: {}", choice));
    }

    @Test
    public void getCorrectChoiceByQuestionIdTest() {
        log.info("correct choice: {}", quizChoiceService.getCorrectChoiceByQuestionId(1L));
    }

    @Test
    public void getChoiceByIdTest() {
        log.info("choice: {}", quizChoiceService.getChoiceById(1L));
    }

}
