package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.WordsResponseDTO;

import java.util.List;

public interface WordsService {

    // 학습별 단어 목록 조회
    public List<WordsResponseDTO> getWordsByEduId(Long eduId);

    // 단어 상세 조회
    public WordsResponseDTO getWordById(Long id);
}
