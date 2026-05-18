package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.WordStudyResponseDTO;
import com.app.springapp.domain.vo.WordStudyVO;
import com.app.springapp.mapper.WordStudyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WordStudyDAO {
    private final WordStudyMapper wordStudyMapper;

    // 단어 학습 기록 등록
    public void save(WordStudyVO wordStudyVO) {
        wordStudyMapper.insert(wordStudyVO);
    }

    // 단어 학습 기록 조회
    public Optional<WordStudyResponseDTO> findByUserIdAndEduWordMapId(Long userId, Long eduWordMapId) {
        return Optional.ofNullable(wordStudyMapper.select(userId, eduWordMapId));
    }

    // 학습별 완료 단어 개수 조회
    public int countCompletedWordsByEduId(Long userId, Long eduId) {
        return wordStudyMapper.countCompletedByEduId(userId, eduId);
    }

    // 학습별 전체 단어 개수 조회
    public int countTotalWordsByEduId(Long eduId) {
        return wordStudyMapper.countTotalByEduId(eduId);
    }

    // 단어 학습 완료 상태 수정
    public void update(WordStudyVO wordStudyVO) {
        wordStudyMapper.update(wordStudyVO);
    }
}
