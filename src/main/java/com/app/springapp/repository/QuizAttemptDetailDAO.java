package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.QuizAttemptDetailResponseDTO;
import com.app.springapp.domain.vo.QuizAttemptDetailVO;
import com.app.springapp.mapper.QuizAttemptDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuizAttemptDetailDAO {
    private final QuizAttemptDetailMapper quizAttemptDetailMapper;

    // 퀴즈 문제별 응시 결과 등록
    public void save(QuizAttemptDetailVO quizAttemptDetailVO) {
        quizAttemptDetailMapper.insert(quizAttemptDetailVO);
    }

    // 퀴즈 응시별 문제 결과 목록 조회
    public List<QuizAttemptDetailResponseDTO> findDetailsByAttemptId(Long quizAttemptId) {
        return quizAttemptDetailMapper.selectByAttemptId(quizAttemptId);
    }

}
