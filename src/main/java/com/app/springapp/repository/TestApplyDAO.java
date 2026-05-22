package com.app.springapp.repository;

import com.app.springapp.domain.dto.TestApplyDTO;
import com.app.springapp.domain.vo.TestApplyVO;
import com.app.springapp.mapper.TestApplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestApplyDAO {
    private final TestApplyMapper testApplyMapper;

    // 원서 접수 등록
    public void save(TestApplyVO testApplyVO) {
        testApplyMapper.insert(testApplyVO);
    }

    // 특정 시험의 현재 신청 인원 수 조회
    public int countByTestId(Long testId) {
        return testApplyMapper.countByTestId(testId);
    }

    // 접수 취소
    public void deleteById(Long id, Long userId) {
        testApplyMapper.deleteById(id, userId);
    }

    // 내 접수 목록 조회
    public List<TestApplyDTO> findByUserId(Long userId) {
        return testApplyMapper.selectByUserId(userId);
    }
}
