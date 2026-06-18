package com.app.springapp.service;

import com.app.springapp.config.FastApiConfig;
import com.app.springapp.domain.dto.request.LearningAnalysisAnswerRequestDTO;
import com.app.springapp.domain.dto.request.LearningAnalysisRequestDTO;
import com.app.springapp.domain.dto.request.LearningAnalysisResultRequestDTO;
import com.app.springapp.domain.dto.response.LearningAnalysisAnswerResponseDTO;
import com.app.springapp.domain.dto.response.LearningAnalysisResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningResultResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningSummaryResponseDTO;
import com.app.springapp.exception.LearningAnalysisException;
import com.app.springapp.exception.MyPageException;
import com.app.springapp.repository.MyPageLearningDAO;
import com.app.springapp.repository.UserAttendanceDAO;
import com.app.springapp.repository.UserDAO;
import com.app.springapp.repository.UserRewardHistoryDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {MyPageException.class, LearningAnalysisException.class})
@Slf4j
public class MyPageLearningServiceImpl implements MyPageLearningService {
    private final MyPageLearningDAO myPageLearningDAO;
    private final UserDAO userDAO;
    private final RestTemplate restTemplate;
    private final FastApiConfig fastApiConfig;
    private final UserAttendanceDAO userAttendanceDAO;
    private final UserRewardHistoryDAO userRewardHistoryDAO;

    // 마이페이지 학습 조회
    @Override
    @Transactional(readOnly = true)
    public MyPageLearningResponseDTO getLearning(Long userId) {
        userDAO.findUserById(userId)
                .orElseThrow(() -> new MyPageException("회원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        MyPageLearningResponseDTO learning = new MyPageLearningResponseDTO();

        learning.setStatusList(myPageLearningDAO.findLearningStatusListByUserId(userId));
        learning.setResultList(myPageLearningDAO.findLearningResultListByUserId(userId));

        MyPageLearningSummaryResponseDTO summary = myPageLearningDAO.findLearningSummaryByUserId(userId);

        if (summary == null) {
            summary = new MyPageLearningSummaryResponseDTO();
            summary.setTotalAccuracy(0L);
            summary.setTotalQuestionCount(0L);
            summary.setTotalStudyTime(0L);
        }

        learning.setSummary(summary);

        return learning;
    }

    // AI 학습 분석 조회
    @Override
    @Transactional(readOnly = true)
    public LearningAnalysisResponseDTO getLearningAnalysis(Long userId) {
        userDAO.findUserById(userId)
                .orElseThrow(() -> new MyPageException("회원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        List<MyPageLearningResultResponseDTO> resultList =
                myPageLearningDAO.findLearningResultListByUserId(userId);

        if (resultList == null || resultList.isEmpty()) {
            throw new LearningAnalysisException("분석할 학습 결과가 없습니다.", HttpStatus.NOT_FOUND);
        }

        LearningAnalysisRequestDTO requestDTO = createLearningAnalysisRequest(userId, resultList);

        try {
            // FastAPI 요청 JSON 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<LearningAnalysisRequestDTO> requestEntity =
                    new HttpEntity<>(requestDTO, headers);

            return restTemplate.postForObject(
                    fastApiConfig.getReportUrl(),
                    requestEntity,
                    LearningAnalysisResponseDTO.class
            );
        } catch (Exception e) {
            log.error("AI 학습 분석 요청 실패", e);
            throw new LearningAnalysisException("AI 학습 분석 결과를 불러오지 못했습니다.", HttpStatus.BAD_GATEWAY);
        }
    }

    // FastAPI 요청 데이터 생성
    private LearningAnalysisRequestDTO createLearningAnalysisRequest(
            Long userId,
            List<MyPageLearningResultResponseDTO> resultList
    ) {
        LearningAnalysisRequestDTO requestDTO = new LearningAnalysisRequestDTO();

        Long streakDays = (long) userAttendanceDAO.findConsecutiveDays(userId);
        Long learnExp = (long) userRewardHistoryDAO.findLearnRewardExpByUserId(userId);

        requestDTO.setUserId(userId);
        requestDTO.setStreakDays(streakDays);
        requestDTO.setExp(learnExp);

        List<LearningAnalysisResultRequestDTO> results = resultList.stream()
                .map(this::toLearningAnalysisResultRequest)
                .toList();

        requestDTO.setResults(results);

        return requestDTO;
    }

    // 학습 결과 요청 변환
    private LearningAnalysisResultRequestDTO toLearningAnalysisResultRequest(
            MyPageLearningResultResponseDTO result
    ) {
        LearningAnalysisResultRequestDTO requestDTO = new LearningAnalysisResultRequestDTO();

        requestDTO.setId(result.getQuizAttemptId());
        requestDTO.setTitle(result.getQuizTitle());
        requestDTO.setCategory(resolveLearningCategory(result));
        requestDTO.setCorrectCount(defaultLong(result.getCorrectCount()));
        requestDTO.setTotalCount(defaultLong(result.getTotalCount()));
        requestDTO.setStudyTimeMinutes(convertSecondsToMinutes(result.getSpentTime()));

        List<LearningAnalysisAnswerResponseDTO> answers =
                findLearningAnalysisAnswerList(result);

        requestDTO.setAnswers(answers.stream()
                .map(this::toLearningAnalysisAnswerRequest)
                .toList());

        return requestDTO;
    }

    // 문제별 정답 조회
    private List<LearningAnalysisAnswerResponseDTO> findLearningAnalysisAnswerList(
            MyPageLearningResultResponseDTO result
    ) {
        if ("QUIZ".equals(result.getResultType())) {
            return myPageLearningDAO.findLearningAnalysisAnswerListByAttemptId(
                    result.getQuizAttemptId()
            );
        }

        if ("LEARN".equals(result.getResultType())) {
            return myPageLearningDAO.findLearningAnalysisEduAnswerListByEduStartId(
                    result.getQuizAttemptId()
            );
        }

        return List.of();
    }

    // 문제별 정답 요청 변환
    private LearningAnalysisAnswerRequestDTO toLearningAnalysisAnswerRequest(
            LearningAnalysisAnswerResponseDTO answer
    ) {
        LearningAnalysisAnswerRequestDTO requestDTO = new LearningAnalysisAnswerRequestDTO();

        requestDTO.setQuestionNumber(answer.getQuestionNumber());
        requestDTO.setCorrect(Boolean.TRUE.equals(answer.getIsCorrect()));

        return requestDTO;
    }

    // 학습 카테고리 구분
    private String resolveLearningCategory(MyPageLearningResultResponseDTO result) {
        if ("LEARN".equals(result.getResultType())) {
            return "학습 > 수어";
        }

        String title = result.getQuizTitle() == null ? "" : result.getQuizTitle();

        if (title.contains("모스")) {
            return "수신호 > 모스부호";
        }

        if (title.contains("점자")) {
            return "수신호 > 점자";
        }

        return "학습 > 수어";
    }

    // 초 단위 시간 분 단위 변환
    private Long convertSecondsToMinutes(Long seconds) {
        if (seconds == null || seconds <= 0) {
            return 0L;
        }

        return Math.max(1L, Math.round(seconds / 60.0));
    }

    // null 숫자 기본값
    private Long defaultLong(Long value) {
        return value == null ? 0L : value;
    }
}