package com.app.springapp.mapper;

import com.app.springapp.domain.vo.TestApplyVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestApplyMapper {
    // 원서 접수 등록
    void insert(TestApplyVO testApplyVO);

    // 특정 시험의 현재 신청 인원 수 조회
    int countByTestId(Long testId);
}
