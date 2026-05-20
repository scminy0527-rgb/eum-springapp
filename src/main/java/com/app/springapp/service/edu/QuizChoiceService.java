package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.QuizChoiceResponseDTO;

import java.util.List;

public interface QuizChoiceService {
    // 문제별 보기 목록 조회
    public List<QuizChoiceResponseDTO> getChoicesByQuestionId(Long quizQuestionId);

    // 정답 보기 조회
    public QuizChoiceResponseDTO getCorrectChoiceByQuestionId(Long quizQuestionId);

    // 보기 상세 조회
    public QuizChoiceResponseDTO getChoiceById(Long id);
}
