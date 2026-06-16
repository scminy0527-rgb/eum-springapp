package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.EduStartResponseDTO;
import com.app.springapp.domain.vo.EduStartVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduStartMapper {

    // 사용자와 학습 번호로 시작 기록 조회
    public EduStartResponseDTO selectByUserIdAndEduId(Long userId, Long eduId);

    // 학습 시작 기록 등록
    public void insert(EduStartVO eduStartVO);

    // 사용자의 특정 학습 최신 미완료 시작 기록 완료 처리
    public void updateCompleted(Long userId, Long eduId, int eduStartTime);

    // 사용자의 특정 학습 최신 완료 세션 조회
    public EduStartResponseDTO selectLatestCompletedByUserIdAndEduId(Long userId, Long eduId);

    // 학습 세션 완료 여부 조회
    public int countCompletedByUserIdAndEduId(Long userId, Long eduId);

    // 사용자의 특정 학습 미완료 시작 기록 개수 조회
    public int countIncompleteByUserIdAndEduId(Long userId, Long eduId);

    // 학습 세션 문제 풀이 결과 반영
    public void updateProgress(Long id, int isCorrect);
}