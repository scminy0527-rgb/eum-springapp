package com.app.springapp.service;

import com.app.springapp.domain.dto.response.WordStudyResponseDTO;
import com.app.springapp.domain.vo.WordStudyVO;

public interface WordStudyService {

    // 단어 학습 기록 등록 -> 완료 처리
    public void finishWordStudy(WordStudyVO wordStudyVO);

    // 단어 학습 기록 조회
    public WordStudyResponseDTO getWordStudy(Long userId, Long eduWordMapId);

    // 학습별 완료 단어 개수 조회
    public int getCompletedWordCount(Long userId, Long eduId);

    // 학습별 전체 단어 개수 조회
    public int getTotalWordCount(Long eduId);

    // 오늘 완료한 단어 개수 조회
    public int getTodayCompletedWordCount(Long userId);

    // 단어 학습 완료 상태 수정 -> 일단 빼둠
    // public void changeWordStudyStatus(WordStudyVO wordStudyVO);
}
