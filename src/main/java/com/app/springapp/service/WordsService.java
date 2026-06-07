package com.app.springapp.service;

import com.app.springapp.domain.dto.response.WordsResponseDTO;
import com.app.springapp.domain.vo.WordsVO;

import java.util.List;

public interface WordsService {

    // 학습별 단어 목록 조회
    public List<WordsResponseDTO> getWordsByEduId(Long eduId);

    // 학습별 랜덤 단어 목록 조회
    public List<WordsResponseDTO> getRandomWordsByEduId(Long eduId, int limit);

    // 단어 상세 조회
    public WordsResponseDTO getWordById(Long id);

    // 관리자용
    // 단어 등록
    public void saveWord(WordsVO wordsVO);

    // 단어 수정
    public void updateWord(WordsVO wordsVO);
}
