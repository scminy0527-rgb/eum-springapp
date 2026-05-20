package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.QuizResponseDTO;

import java.util.List;

public interface QuizService {
    // 퀴즈 목록 조회
    public List<QuizResponseDTO> getQuizList();

    // 퀴즈 상세 조회
    public QuizResponseDTO getQuizById(Long id);
}
