package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.MyPageLearningResultResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningStatusResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningSummaryResponseDTO;
import com.app.springapp.mapper.MyPageLearningMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPageLearningDAO {
    private final MyPageLearningMapper myPageLearningMapper;

    //    마이페이지 학습현황 조회
    public List<MyPageLearningStatusResponseDTO> findLearningStatusListByUserId(Long userId) {
        return myPageLearningMapper.selectLearningStatusListByUserId(userId);
    }

    //    마이페이지 학습결과 조회
    public List<MyPageLearningResultResponseDTO> findLearningResultListByUserId(Long userId) {
        return myPageLearningMapper.selectLearningResultListByUserId(userId);
    }

    //    마이페이지 학습요약 조회
    public MyPageLearningSummaryResponseDTO findLearningSummaryByUserId(Long userId) {
        return myPageLearningMapper.selectLearningSummaryByUserId(userId);
    }
}