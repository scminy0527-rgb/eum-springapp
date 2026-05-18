package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.WordsResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WordsMapper {

    // 학습별 단어 목록 조회
    public List<WordsResponseDTO> selectByEduId(Long eduId);

    // 단어 상세 조회
    public WordsResponseDTO select(Long id);

}
