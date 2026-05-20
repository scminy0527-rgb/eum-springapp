package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.QuizResponseDTO;
import com.app.springapp.mapper.QuizMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizDAO {
    private final QuizMapper quizMapper;

    // 퀴즈 목록 조회
    public List<QuizResponseDTO> findQuizList() {
        return quizMapper.selectAll();
    }

    // 퀴즈 상세 조회
    public Optional<QuizResponseDTO> findById(Long id) {
        return Optional.ofNullable(quizMapper.select(id));
    }
}
