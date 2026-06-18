package com.app.springapp.repository;

import com.app.springapp.domain.dto.MyTestResultDTO;
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

    // 특정 유저가 특정 시험에 이미 접수했는지 확인
    public boolean existsByUserIdAndTestId(Long userId, Long testId) {
        return testApplyMapper.countByUserIdAndTestId(userId, testId) > 0;
    }

    // 시험 결과 존재 여부 확인
    public boolean hasResult(Long id) {
        return testApplyMapper.countResultByApplyId(id) > 0;
    }

    // 접수 취소
    public int deleteById(Long id, Long userId) {
        return testApplyMapper.deleteById(id, userId);
    }

    // 내 접수 목록 조회
    public List<TestApplyDTO> findByUserId(Long userId) {
        return testApplyMapper.selectByUserId(userId);
    }

    // 내 합격 여부 조회
    public List<MyTestResultDTO> findMyResultsByUserId(Long userId) {
        return testApplyMapper.selectMyResultsByUserId(userId);
    }
}
