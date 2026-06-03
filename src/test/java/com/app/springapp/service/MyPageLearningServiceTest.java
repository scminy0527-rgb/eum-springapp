package com.app.springapp.service;

import com.app.springapp.domain.dto.response.MyPageLearningResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningResultResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningStatusResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningSummaryResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.ai.openai.api-key=test-key",
        "spring.ai.openai.chat.api-key=test-key",
        "toss.secret-key=test-secret-key"
})
@Slf4j
public class MyPageLearningServiceTest {

    @Autowired
    private MyPageLearningService myPageLearningService;

    //    마이페이지 학습 페이지 조회 테스트
    @Test
    public void getLearningTest() {
        Long userId = 1L;

        MyPageLearningResponseDTO learning = myPageLearningService.getLearning(userId);

        log.info("마이페이지 학습 페이지 조회 결과: {}", learning);

        assertThat(learning).isNotNull();
        assertThat(learning.getStatusList()).isNotNull();
        assertThat(learning.getResultList()).isNotNull();
        assertThat(learning.getSummary()).isNotNull();
    }

    //    마이페이지 학습현황 조회 테스트
    @Test
    public void getLearningStatusListTest() {
        Long userId = 1L;

        MyPageLearningResponseDTO learning = myPageLearningService.getLearning(userId);

        learning.getStatusList()
                .stream()
                .forEach((status) -> log.info("학습현황: {}", status));

        assertThat(learning.getStatusList()).isNotNull();

        for (MyPageLearningStatusResponseDTO status : learning.getStatusList()) {
            assertThat(status.getEduId()).isNotNull();
            assertThat(status.getEduTitle()).isNotNull();
            assertThat(status.getProgress()).isNotNull();
            assertThat(status.getStudyTime()).isNotNull();
        }
    }

    //    마이페이지 학습결과 조회 테스트
    @Test
    public void getLearningResultListTest() {
        Long userId = 1L;

        MyPageLearningResponseDTO learning = myPageLearningService.getLearning(userId);

        learning.getResultList()
                .stream()
                .forEach((result) -> log.info("학습결과: {}", result));

        assertThat(learning.getResultList()).isNotNull();

        for (MyPageLearningResultResponseDTO result : learning.getResultList()) {
            assertThat(result.getQuizAttemptId()).isNotNull();
            assertThat(result.getQuizId()).isNotNull();
            assertThat(result.getQuizTitle()).isNotNull();
            assertThat(result.getCorrectCount()).isNotNull();
            assertThat(result.getTotalCount()).isNotNull();
            assertThat(result.getSpentTime()).isNotNull();
            assertThat(result.getAccuracy()).isNotNull();
        }
    }

    //    마이페이지 학습요약 조회 테스트
    @Test
    public void getLearningSummaryTest() {
        Long userId = 1L;

        MyPageLearningResponseDTO learning = myPageLearningService.getLearning(userId);
        MyPageLearningSummaryResponseDTO summary = learning.getSummary();

        log.info("학습요약: {}", summary);

        assertThat(summary).isNotNull();
        assertThat(summary.getTotalAccuracy()).isNotNull();
        assertThat(summary.getTotalQuestionCount()).isNotNull();
        assertThat(summary.getTotalStudyTime()).isNotNull();
    }
}