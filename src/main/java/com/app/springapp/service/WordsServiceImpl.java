package com.app.springapp.service;

import com.app.springapp.domain.dto.response.WordsResponseDTO;
import com.app.springapp.domain.vo.WordsVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.WordsDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class WordsServiceImpl implements WordsService {
    private final WordsDAO wordsDAO;

    // 학습별 단어 목록 조회
    @Override
    public List<WordsResponseDTO> getWordsByEduId(Long eduId) {
        return wordsDAO.findWordsByEduId(eduId);
    }

    // 단어 상세 조회
    @Override
    public WordsResponseDTO getWordById(Long id) {
        return wordsDAO.findWordById(id).orElseThrow(() -> new EduException("단어 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 관리자
    // 단어 등록
    @Override
    public void saveWord(WordsVO wordsVO) {
        wordsDAO.save(wordsVO);
    }

    // 단어 수정
    @Override
    public void updateWord(WordsVO wordsVO) {
        wordsDAO.update(wordsVO);
    }
}
