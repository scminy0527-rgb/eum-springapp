package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.request.QuizSubmitAnswerRequestDTO;
import com.app.springapp.domain.dto.request.QuizSubmitRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class QuizAttemptServiceTests {
    @Autowired
    private QuizAttemptService quizAttemptService;

    @Test
    public void submitQuizTest() {
        QuizSubmitAnswerRequestDTO answer = new QuizSubmitAnswerRequestDTO();
        answer.setQuizQuestionId(1L);
        answer.setQuizChoiceId(1L);

        QuizSubmitRequestDTO requestDTO = new QuizSubmitRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setAnswers(List.of(answer));

        log.info("attempt: {}", quizAttemptService.submitQuiz(1L, requestDTO));
    }

    @Test
    public void getAttemptByIdTest() {
        log.info("attempt: {}", quizAttemptService.getAttemptById(1L));
    }

    @Test
    public void getAttemptsByUserIdTest() {
        quizAttemptService.getAttemptsByUserId(1L).forEach(attempt -> log.info("attempt: {}", attempt));
    }

    @Test
    public void getAttemptDetailsByAttemptIdTest() {
        quizAttemptService.getAttemptDetailsByAttemptId(1L).forEach(detail -> log.info("detail: {}", detail));
    }
}
