package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.MyPageLearningResultResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningStatusResponseDTO;
import com.app.springapp.domain.dto.response.MyPageLearningSummaryResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageLearningMapper {
    //    마이페이지 학습현황 조회
    List<MyPageLearningStatusResponseDTO> selectLearningStatusListByUserId(Long userId);

    //    마이페이지 학습결과 조회
    List<MyPageLearningResultResponseDTO> selectLearningResultListByUserId(Long userId);

    //    마이페이지 학습요약 조회
    MyPageLearningSummaryResponseDTO selectLearningSummaryByUserId(Long userId);
}