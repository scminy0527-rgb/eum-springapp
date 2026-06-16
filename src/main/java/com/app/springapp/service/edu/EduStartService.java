package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.EduStartCompleteResponseDTO;

public interface EduStartService {

    // 학습 시작 기록 저장
    public void startEdu(Long userId, Long eduId);

    // 학습 세션 완료 처리
    public EduStartCompleteResponseDTO completeEduStart(Long userId, Long eduId, int eduStartTime);

    // 학습 세션 완료 여부 조회
    public boolean isEduStartCompleted(Long userId, Long eduId);

    // 학습 로드맵 이벤트 보상 수령
    public int claimRoadmapReward(Long userId, Long eduId);

    // 학습 세션 문제 풀이 결과 반영
    public void recordProgress(Long userId, Long eduId, int isCorrect);

}
