package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.WordsResponseDTO;
import com.app.springapp.domain.vo.WordsVO;
import io.swagger.v3.oas.models.OpenAPI;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WordsMapper {

    // 학습별 단어 목록 조회
    public List<WordsResponseDTO> selectByEduId(Long eduId);

    // 학습별 랜덤 단어 목록 조회
    public List<WordsResponseDTO> selectRandomByEduId(@Param("eduId") Long eduId, @Param("limit") int limit);

    // 단어 상세 조회
    public WordsResponseDTO select(Long id);

    // OpenAPI 수어 번호로 조회
    public WordsResponseDTO selectBySignWordId(Long signWordId);

    // 관리자용
    // 단어 등록
    public void insert(WordsVO wordsVO);

    // 단어 수정
    public void update(WordsVO wordsVO);

}
