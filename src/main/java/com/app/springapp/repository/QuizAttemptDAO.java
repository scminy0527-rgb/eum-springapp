package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.QuizAttemptResponseDTO;
import com.app.springapp.domain.vo.QuizAttemptVO;
import com.app.springapp.mapper.QuizAttemptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizAttemptDAO {
    private final QuizAttemptMapper quizAttemptMapper;

    // 퀴즈 응시 기록 등록
    public void save(QuizAttemptVO quizAttemptVO) {
        quizAttemptMapper.insert(quizAttemptVO);
    }

    // 퀴즈 응시 기록 상세 조회
    public Optional<QuizAttemptResponseDTO> findById(Long id) {
        return Optional.ofNullable(quizAttemptMapper.select(id));
    }

    // 사용자별 퀴즈 응시 기록 조회
    public List<QuizAttemptResponseDTO> findAttemptsByUserId(Long userId) {
        return quizAttemptMapper.selectByUserId(userId);
    }

    // 퀴즈 응시 점수 수정
    public void updateScore(QuizAttemptVO quizAttemptVO) {
        quizAttemptMapper.updateScore(quizAttemptVO);
    }
}
