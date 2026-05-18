package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.WordsResponseDTO;
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

    // 단어 상세 조회
    public Optional<WordsResponseDTO> findWordById(Long id) {
        return Optional.ofNullable(wordsMapper.select(id));
    }

}
