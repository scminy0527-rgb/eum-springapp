package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.QuizChoiceResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuizChoiceMapper {

    // 문제별 보기 목록 조회
    public List<QuizChoiceResponseDTO> selectByQuestionId(Long quizQuestionId);

    // 정답 보기 조회 -> 정답 비교
    public QuizChoiceResponseDTO selectCorrectByQuestionId(Long quizQuestionId);

    // 보기 상세 조회
    public QuizChoiceResponseDTO select(Long id);
}
