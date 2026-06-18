package com.app.springapp.service;

import com.app.springapp.domain.dto.response.LearningAnalysisResponseDTO;
import com.app.springapp.domain.dto.response.LearningIndividualAnalysisResponseDTO;
import com.app.springapp.domain.dto.response.LearningOverallAnalysisResponseDTO;
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
        "toss.secret-key=test-secret-key",
        "fastapi.learning-analysis.base-url=http://localhost:8000",
        "fastapi.learning-analysis.report-path=/ai/learning-analysis/report"
})
@Slf4j
public class MyPageLearningServiceTest {

    @Autowired
    private MyPageLearningService myPageLearningService;

    // 마이페이지 학습 페이지 조회 테스트
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

    // 마이페이지 학습현황 조회 테스트
    @Test
    public void getLearningStatusListTest() {
        Long userId = 1L;

        MyPageLearningResponseDTO learning = myPageLearningService.getLearning(userId);

        learning.getStatusList()
                .forEach((status) -> log.info("학습현황: {}", status));

        assertThat(learning.getStatusList()).isNotNull();

        for (MyPageLearningStatusResponseDTO status : learning.getStatusList()) {
            assertThat(status.getEduId()).isNotNull();
            assertThat(status.getEduTitle()).isNotNull();
            assertThat(status.getProgress()).isNotNull();
            assertThat(status.getStudyTime()).isNotNull();
        }
    }

    // 마이페이지 학습결과 조회 테스트
    @Test
    public void getLearningResultListTest() {
        Long userId = 1L;

        MyPageLearningResponseDTO learning = myPageLearningService.getLearning(userId);

        learning.getResultList()
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

    // 마이페이지 학습요약 조회 테스트
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

    // AI 학습 분석 전체 응답 테스트
    @Test
    public void getLearningAnalysisTest() {
        Long userId = 1L;

        LearningAnalysisResponseDTO analysis = myPageLearningService.getLearningAnalysis(userId);

        log.info("AI 학습 분석 결과: {}", analysis);

        assertThat(analysis).isNotNull();
        assertThat(analysis.getReport()).isNotNull();
        assertThat(analysis.getOverallAnalysisList()).isNotNull();
        assertThat(analysis.getIndividualAnalysisList()).isNotNull();
        assertThat(analysis.getOverallAnalysisList()).isNotEmpty();
        assertThat(analysis.getIndividualAnalysisList()).isNotEmpty();
    }

    // AI 학습 분석 종합 리포트 테스트
    @Test
    public void getLearningAnalysisReportTest() {
        Long userId = 1L;

        LearningAnalysisResponseDTO analysis = myPageLearningService.getLearningAnalysis(userId);

        assertThat(analysis.getReport().getTotalRate()).isNotNull();
        assertThat(analysis.getReport().getGrade()).isNotBlank();
        assertThat(analysis.getReport().getMessage()).isNotBlank();
        assertThat(analysis.getReport().getAverageRate()).isNotBlank();
        assertThat(analysis.getReport().getSolvedCount()).isNotBlank();
        assertThat(analysis.getReport().getStudyTime()).isNotBlank();
        assertThat(analysis.getReport().getStreakDays()).isNotBlank();
        assertThat(analysis.getReport().getExp()).isNotBlank();

        log.info("AI 종합 리포트: {}", analysis.getReport());
    }

    // AI 학습 분석 전체 분석 목록 테스트
    @Test
    public void getLearningOverallAnalysisListTest() {
        Long userId = 1L;

        LearningAnalysisResponseDTO analysis = myPageLearningService.getLearningAnalysis(userId);

        for (LearningOverallAnalysisResponseDTO overall : analysis.getOverallAnalysisList()) {
            assertThat(overall.getTitle()).isNotBlank();
            assertThat(overall.getRate()).isNotNull();
            assertThat(overall.getRate()).isBetween(0L, 100L);
            assertThat(overall.getColor()).isNotBlank();
            assertThat(overall.getDescription()).isNotBlank();
            assertThat(overall.getGuide()).isNotBlank();

            log.info("AI 전체 분석: {}", overall);
        }
    }

    // AI 학습 분석 개별 분석 목록 테스트
    @Test
    public void getLearningIndividualAnalysisListTest() {
        Long userId = 1L;

        LearningAnalysisResponseDTO analysis = myPageLearningService.getLearningAnalysis(userId);

        for (LearningIndividualAnalysisResponseDTO individual : analysis.getIndividualAnalysisList()) {
            assertThat(individual.getId()).isNotNull();
            assertThat(individual.getTitle()).isNotBlank();
            assertThat(individual.getCategory()).isNotBlank();
            assertThat(individual.getRate()).isNotNull();
            assertThat(individual.getRate()).isBetween(0L, 100L);
            assertThat(individual.getStudyTime()).isNotBlank();
            assertThat(individual.getQuestionCount()).isNotNull();
            assertThat(individual.getColor()).isNotBlank();
            assertThat(individual.getAnswers()).isNotNull();
            assertThat(individual.getGuide()).isNotBlank();

            log.info("AI 개별 분석: {}", individual);
        }
    }
}