package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.request.EduWordFromSignWordRequestDTO;
import com.app.springapp.domain.dto.response.SignWordResponseDTO;
import com.app.springapp.domain.dto.response.WordsResponseDTO;
import com.app.springapp.domain.vo.EduVideoVO;
import com.app.springapp.domain.vo.EduWordMapVO;
import com.app.springapp.domain.vo.WordsVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor =  {EduException.class})
public class EduWordMapServiceImpl implements EduWordMapService {
    private final EduWordMapDAO eduWordMapDAO;
    private final EduDAO eduDAO;
    private final WordsDAO wordsDAO;
    private final SignWordDAO signWordDAO;
    private final EduVideoDAO eduVideoDAO;

    // 전체 조회
    @Override
    public List<EduWordMapVO> getEduWordMaps() {
        return eduWordMapDAO.findAll();
    }

    // 학습별 매핑 조회
    @Override
    public List<EduWordMapVO> getEduWordMapsByEduId(Long eduId) {
        return eduWordMapDAO.findByEduId(eduId);
    }

    // 단어별 매핑 조회
    @Override
    public List<EduWordMapVO> getEduWordMapsByWordsId(Long wordsId) {
        return eduWordMapDAO.findByWordId(wordsId);
    }

    // *학습 + 중복 확인용
    @Override
    public EduWordMapVO getEduWordMapByEduIdAndWordsId(Long eduId, Long wordsId) {
        return eduWordMapDAO.findByEduIdAndWordsId(eduId, wordsId).orElseThrow(() -> new EduException("학습 단어 매핑 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // *학습별 단어 개수
    @Override
    public int getEduWordMapCountByEduId(Long eduId) {
        return eduWordMapDAO.countByEduId(eduId);
    }

    // *매핑 등록
    @Override
    public void saveEduWordMap(EduWordMapVO eduWordMapVO) {
        // 학습 ID가 실제로 있는지 먼저 확인
        eduDAO.findByEduId(eduWordMapVO.getEduId())
                .orElseThrow(() -> new EduException("학습 정보를 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        // 단어 ID가 실제로 있는지 먼저 확인
        wordsDAO.findWordById(eduWordMapVO.getWordsId())
                .orElseThrow(() -> new EduException("단어 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // 같은 학습에 같은 단어가 이미 연결되어 있는지 확인
        if(eduWordMapDAO.findByEduIdAndWordsId(
                eduWordMapVO.getEduId(),
                eduWordMapVO.getWordsId()
        ).isPresent()){
            throw new EduException("이미 등록된 학습 단어 매핑입니다.",  HttpStatus.CONFLICT);
        }
        eduWordMapDAO.save(eduWordMapVO);
    }

    // 매핑 수정
    @Override
    public void updateEduWordMap(EduWordMapVO eduWordMapVO) {
        eduWordMapDAO.update(eduWordMapVO);
    }

    // ID 기준 삭제
    @Override
    public void deleteEduWordMap(Long id) {
        eduWordMapDAO.delete(id);
    }

    // 학습 + 단어 기준 삭제
    @Override
    public void deleteEduWordMapByEduIdAndWordsId(Long eduId, Long wordsId) {
        eduWordMapDAO.deleteByWordsId(wordsId, eduId);
    }

    // OpenAPI 수어 데이터를 학습 단어로 등록
    public void saveEduWordFromSignWord(EduWordFromSignWordRequestDTO requestDTO) {
        // 학습 ID가 실제로 있는지 먼저 확인
        eduDAO.findByEduId(requestDTO.getEduId())
                .orElseThrow(() -> new EduException("학습 정보를 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        // OpenAPI 수어 데이터가 실제로 있는지 확인
        SignWordResponseDTO signWord = signWordDAO.findById(requestDTO.getSignWordId())
                .orElseThrow(() -> new EduException("수어 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        String signWordDetail = signWord.getSignWordDescription() != null && !signWord.getSignWordDescription().isBlank()
                ? signWord.getSignWordDescription()
                : "수어 설명이 준비되지 않았습니다.";

        String signWordImage = signWord.getSignWordThumbnailUrl() != null && !signWord.getSignWordThumbnailUrl().isBlank()
                        ? signWord.getSignWordThumbnailUrl()
                        : signWord.getSignWordImages();

        if (signWordImage == null || signWordImage.isBlank()) {
            signWordImage = "default.jpg";
        }

        Long wordsId;

        // 이미 같은 OpenAPI 수어로 만든 학습 단어가 있는지 확인
        WordsResponseDTO savedWord = wordsDAO.findWordBySignWordId(requestDTO.getSignWordId()).orElse(null);

        if (savedWord != null) {
            wordsId = savedWord.getId();
        } else {
            Long eduVideoId = null;

            // 영상 URL이 있으면 학습 영상으로 등록
            if (signWord.getSignWordVideoUrl() != null && !signWord.getSignWordVideoUrl().isBlank()) {
                EduVideoVO eduVideoVO = new EduVideoVO();
                eduVideoVO.setEduVideoTitle(signWord.getSignWordTitle() + " 수어 영상");
                eduVideoVO.setEduVideoDetail(signWordDetail);
                eduVideoVO.setEduVideoType("openapi");
                eduVideoVO.setEduVideoUrl(signWord.getSignWordVideoUrl());

                eduVideoDAO.save(eduVideoVO);
                eduVideoId = eduVideoVO.getId();
            }

            // OpenAPI 수어 데이터를 학습 단어로 등록
            WordsVO wordsVO = new WordsVO();
            wordsVO.setWordsTitle(signWord.getSignWordTitle());
            wordsVO.setWordsDetail(signWordDetail);
            wordsVO.setWordsImage(signWordImage);
            wordsVO.setWordsType(requestDTO.getWordsType());
            wordsVO.setEduVideoId(eduVideoId);
            wordsVO.setSignWordId(requestDTO.getSignWordId());

            wordsDAO.save(wordsVO);
            wordsId = wordsVO.getId();
        }

        // 이미 같은 학습에 연결되어 있으면 막기
        if (eduWordMapDAO.findByEduIdAndWordsId(requestDTO.getEduId(), wordsId).isPresent()) {
            throw new EduException("이미 등록된 학습 단어 매핑입니다.", HttpStatus.CONFLICT);
        }

        // 학습과 단어 연결
        EduWordMapVO eduWordMapVO = new EduWordMapVO();
        eduWordMapVO.setEduId(requestDTO.getEduId());
        eduWordMapVO.setWordsId(wordsId);

        eduWordMapDAO.save(eduWordMapVO);
    }

}


// TBL_SIGN_WORD id 5
// → TBL_EDU_VIDEO id 3 생성
// → TBL_WORDS id 6 생성
// → TBL_EDU_WORD_MAP id 11 생성
// → /api/words/edu/1에서 조회됨
// → /api/edu-videos/3에서 영상 URL 조회됨