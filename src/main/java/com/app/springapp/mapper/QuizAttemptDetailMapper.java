package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.QuizAttemptDetailResponseDTO;
import com.app.springapp.domain.vo.QuizAttemptDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuizAttemptDetailMapper {
    // 퀴즈 문제별 응시 결과 등록
    public void insert(QuizAttemptDetailVO quizAttemptDetailVO);

    // 퀴즈 응시별 문제 결과 목록 조회
    public List<QuizAttemptDetailResponseDTO> selectByAttemptId(Long quizAttemptId);

}
