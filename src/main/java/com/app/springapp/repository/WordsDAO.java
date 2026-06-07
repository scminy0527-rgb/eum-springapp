package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.WordsResponseDTO;
import com.app.springapp.domain.vo.WordsVO;
import com.app.springapp.mapper.WordsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WordsDAO {
    private final WordsMapper wordsMapper;

    // 학습별 단어 목록 조회
    public List<WordsResponseDTO> findWordsByEduId(Long eduId) {
        return wordsMapper.selectByEduId(eduId);
    }

    // 학습별 랜덤 단어 목록 조회
    public List<WordsResponseDTO> findRandomWordsByEduId(Long eduId, int limit) {
        return wordsMapper.selectRandomByEduId(eduId, limit);
    }

    // 단어 상세 조회
    public Optional<WordsResponseDTO> findWordById(Long id) {
        return Optional.ofNullable(wordsMapper.select(id));
    }

    // OpenAPI 수어 번호로 조회
    public Optional<WordsResponseDTO> findWordBySignWordId(Long signWordId) {
        return Optional.ofNullable(wordsMapper.selectBySignWordId(signWordId));
    }

    // 관리자용
    // 단어 등록
    public void save(WordsVO wordsVO) {
        wordsMapper.insert(wordsVO);
    }

    // 단어 수정
    public void update(WordsVO wordsVO) {
        wordsMapper.update(wordsVO);
    }

}
