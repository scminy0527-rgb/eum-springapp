package com.app.springapp.service;

import com.app.springapp.domain.dto.response.MyPageLearningResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningSummaryResponseDTO;
import com.app.springapp.exception.MyPageException;
import com.app.springapp.repository.MyPageLearningDAO;
import com.app.springapp.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {MyPageException.class})
@Slf4j
public class MyPageLearningServiceImpl implements MyPageLearningService {
    private final MyPageLearningDAO myPageLearningDAO;
    private final UserDAO userDAO;

    //  마이페이지 학습 페이지 조회
    @Override
    @Transactional(readOnly = true)
    public MyPageLearningResponseDTO getLearning(Long userId) {
        //  회원 정보가 없으면 조회를 진행하지 않음
        userDAO.findUserById(userId)
                .orElseThrow(() -> new MyPageException("회원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        MyPageLearningResponseDTO learning = new MyPageLearningResponseDTO();

        //  학습현황, 학습결과, 학습요약을 각각 조회해서 응답 DTO에 담음
        learning.setStatusList(myPageLearningDAO.findLearningStatusListByUserId(userId));
        learning.setResultList(myPageLearningDAO.findLearningResultListByUserId(userId));

        MyPageLearningSummaryResponseDTO summary = myPageLearningDAO.findLearningSummaryByUserId(userId);

        //  학습 데이터가 없는 회원도 기본 요약값을 내려줌
        if (summary == null) {
            summary = new MyPageLearningSummaryResponseDTO();
            summary.setTotalAccuracy(0L);
            summary.setTotalQuestionCount(0L);
            summary.setTotalStudyTime(0L);
        }

        learning.setSummary(summary);

        return learning;
    }
}