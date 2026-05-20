package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.QuizQuestionResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuizQuestionMapper {

    // 퀴즈별 문제 목록 조회
    public List<QuizQuestionResponseDTO> selectByQuizId(Long quizId);

    // 문제 상세 조회
    public QuizQuestionResponseDTO select(Long id);
}
