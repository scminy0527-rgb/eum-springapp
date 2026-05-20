package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.QuizQuestionResponseDTO;
import com.app.springapp.mapper.QuizQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizQuestionDAO {
    private final QuizQuestionMapper quizQuestionMapper;

    // 퀴즈별 문제 목록 조회
    public List<QuizQuestionResponseDTO> findQuestionsByQuizId(Long quizId) {
        return quizQuestionMapper.selectByQuizId(quizId);
    }

    // 문제 상세 조회
    public Optional<QuizQuestionResponseDTO> findById(Long id) {
        return Optional.ofNullable(quizQuestionMapper.select(id));
    }
}
