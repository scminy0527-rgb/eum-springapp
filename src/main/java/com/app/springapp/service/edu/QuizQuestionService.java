package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.QuizQuestionResponseDTO;

import java.util.List;

public interface QuizQuestionService {
    // 퀴즈별 문제 목록 조회
    public List<QuizQuestionResponseDTO> getQuestionsByQuizId(Long quizId);

    // 문제 상세 조회
    public QuizQuestionResponseDTO getQuestionById(Long id);
}
