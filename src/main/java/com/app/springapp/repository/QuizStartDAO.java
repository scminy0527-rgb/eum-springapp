package com.app.springapp.repository;

import com.app.springapp.domain.vo.QuizStartVO;
import com.app.springapp.mapper.QuizStartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizStartDAO {
    private final QuizStartMapper quizStartMapper;

    // 퀴즈 시작 기록 조회
    public Optional<QuizStartVO> findByUserIdAndQuizId(Long userId, Long quizId) {
        return Optional.ofNullable(quizStartMapper.selectByUserIdAndQuizId(userId, quizId));
    }

    // 퀴즈 시작 기록 등록
    public void save(QuizStartVO quizStartVO) {
        quizStartMapper.insert(quizStartVO);
    }

    // 퀴즈 시작 시간 갱신
    public void updateStartAt(Long userId, Long quizId) {
        quizStartMapper.updateStartAt(userId, quizId);
    }

    // 퀴즈 진행 문제 수 증가
    public void updateProgress(Long userId, Long quizId, int totalCount, int isCorrect) {
        quizStartMapper.updateProgress(userId, quizId, totalCount, isCorrect);
    }


}
