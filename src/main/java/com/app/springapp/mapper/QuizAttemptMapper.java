package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.QuizAttemptResponseDTO;
import com.app.springapp.domain.vo.QuizAttemptVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuizAttemptMapper {
    // 퀴즈 응시 기록 등록
    public void insert(QuizAttemptVO quizAttemptVO);

    // 퀴즈 응시 기록 상세 조회
    public QuizAttemptResponseDTO select(Long id);

    // 사용자별 퀴즈 응시 기록 조회
    public List<QuizAttemptResponseDTO> selectByUserId(Long userId);

    // 퀴즈 응시 점수 수정
    public void updateScore(QuizAttemptVO quizAttemptVO);
}
