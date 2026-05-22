package com.app.springapp.mapper;

import com.app.springapp.domain.dto.TestApplyDTO;
import com.app.springapp.domain.vo.TestApplyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TestApplyMapper {
    // 원서 접수 등록
    void insert(TestApplyVO testApplyVO);

    // 특정 시험의 현재 신청 인원 수 조회
    int countByTestId(Long testId);

    // 접수 취소
    void deleteById(@Param("id") Long id, @Param("userId") Long userId);

    // 내 접수 목록 조회
    List<TestApplyDTO> selectByUserId(Long userId);
}
