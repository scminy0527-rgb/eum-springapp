package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.QuizChoiceResponseDTO;
import com.app.springapp.mapper.QuizChoiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizChoiceDAO {
    private final QuizChoiceMapper quizChoiceMapper;

    // 문제별 보기 목록 조회
    public List<QuizChoiceResponseDTO> findChoicesByQuestionId(Long quizQuestionId) {
        return quizChoiceMapper.selectByQuestionId(quizQuestionId);
    }

    // 정답 보기 조회
    public Optional<QuizChoiceResponseDTO> findCorrectChoiceByQuestionId(Long quizQuestionId) {
        return Optional.ofNullable(quizChoiceMapper.selectCorrectByQuestionId(quizQuestionId));
    }

    // 보기 상세 조회
    public Optional<QuizChoiceResponseDTO> findById(Long id) {
        return Optional.ofNullable(quizChoiceMapper.select(id));
    }
}
