package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.EduStartResponseDTO;
import com.app.springapp.domain.vo.EduStartVO;
import com.app.springapp.mapper.EduStartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EduStartDAO {
    private final EduStartMapper eduStartMapper;

    // 사용자와 학습 번호로 시작 기록 조회
    public Optional<EduStartResponseDTO> findByUserIdAndEduId(Long userId, Long eduId) {
        return Optional.ofNullable(eduStartMapper.selectByUserIdAndEduId(userId, eduId));
    }

    // 학습 시작 기록 등록
    public void save(EduStartVO eduStartVO) {
        eduStartMapper.insert(eduStartVO);
    }

    // 학습 세션 완료 처리
    public void updateCompleted(Long userId, Long eduId, int eduStartTime) {
        eduStartMapper.updateCompleted(userId, eduId, eduStartTime);
    }

    // 사용자의 특정 학습 최신 완료 세션 조회
    public EduStartResponseDTO findLatestCompletedByUserIdAndEduId(Long userId, Long eduId) {
        return eduStartMapper.selectLatestCompletedByUserIdAndEduId(userId, eduId);
    }

    // 학습 세션 문제 풀이 결과 반영
    public void updateProgress(Long id, int isCorrect) {
        eduStartMapper.updateProgress(id, isCorrect);
    }

    // 학습 세션 완료 여부 조회
    public boolean existsCompletedEduStart(Long userId, Long eduId) {
        return eduStartMapper.countCompletedByUserIdAndEduId(userId, eduId) > 0;
    }

    // 사용자의 특정 학습 미완료 시작 기록 존재 여부 조회
    public boolean existsIncompleteEduStart(Long userId, Long eduId) {
        return eduStartMapper.countIncompleteByUserIdAndEduId(userId, eduId) > 0;
    }
}
