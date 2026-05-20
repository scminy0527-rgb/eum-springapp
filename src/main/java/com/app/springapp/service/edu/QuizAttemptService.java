package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.request.QuizSubmitRequestDTO;
import com.app.springapp.domain.dto.response.QuizAttemptDetailResponseDTO;
import com.app.springapp.domain.dto.response.QuizAttemptResponseDTO;

import java.util.List;

public interface QuizAttemptService {

    // 퀴즈 제출 및 채점
    public QuizAttemptResponseDTO submitQuiz(Long quizId, QuizSubmitRequestDTO requestDTO);

    // 퀴즈 응시 결과 조회
    public QuizAttemptResponseDTO getAttemptById(Long id);

    // 사용자별 퀴즈 응시 기록 조회
    public List<QuizAttemptResponseDTO> getAttemptsByUserId(Long userId);

    // 퀴즈 응시별 문제 결과 목록 조회
    public List<QuizAttemptDetailResponseDTO> getAttemptDetailsByAttemptId(Long quizAttemptId);
}
