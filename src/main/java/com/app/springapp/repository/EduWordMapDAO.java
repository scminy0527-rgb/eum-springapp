package com.app.springapp.repository;

import com.app.springapp.domain.vo.EduWordMapVO;
import com.app.springapp.mapper.EduWordMapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EduWordMapDAO {
    private final EduWordMapMapper eduWordMapMapper;

    // 전체 조회
    public List<EduWordMapVO> findAll() {
        return eduWordMapMapper.selectAll();
    }

    // *학습별 매핑 조회
    public List<EduWordMapVO> findByEduId(Long eduId) {
        return eduWordMapMapper.selectByEduId(eduId);
    }

    // 단어별 매핑 조회
    public List<EduWordMapVO> findByWordId(Long wordId) {
        return eduWordMapMapper.selectByWordsId(wordId);
    }

    // *학습 + 중복 확인용
    public Optional<EduWordMapVO> findByEduIdAndWordsId(Long eduId, Long wordsId) {
        return Optional.ofNullable(eduWordMapMapper.selectByEduIdAndWordsId(eduId, wordsId));
    }

    // *학습별 단어 개수
    public int countByEduId(Long eduId) {
        return eduWordMapMapper.countByEduId(eduId);
    }

    // *매핑 등록
    public void save(EduWordMapVO eduWordMapVO) {
        eduWordMapMapper.insert(eduWordMapVO);
    }

    // 매핑 수정
    public void update(EduWordMapVO eduWordMapVO) {
        eduWordMapMapper.update(eduWordMapVO);
    }

    // ID 기준 삭제
    public void delete(Long id) {
        eduWordMapMapper.delete(id);
    }

    // 학습 + 단어 기준 삭제
    public void deleteByWordsId(Long eduId, Long wordsId) {
        eduWordMapMapper.deleteByEduIdAndWordsId(eduId, wordsId);
    }




}
