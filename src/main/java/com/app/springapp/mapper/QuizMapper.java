package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.QuizResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuizMapper {

    // 퀴즈 목록 조회
    public List<QuizResponseDTO> selectAll();

    // 퀴즈 상세 조회
    public QuizResponseDTO select(Long id);
}
