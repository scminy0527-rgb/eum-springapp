package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.request.EduWordFromSignWordRequestDTO;
import com.app.springapp.domain.vo.EduWordMapVO;

import java.util.List;
import java.util.Optional;

public interface EduWordMapService {

    // 전체 조회
    public List<EduWordMapVO> getEduWordMaps();

    // *학습별 매핑 조회
    public List<EduWordMapVO> getEduWordMapsByEduId(Long eduId);

    // 단어별 매핑 조회
    public List<EduWordMapVO> getEduWordMapsByWordsId(Long wordsId);

    // *학습 + 중복 확인용
    public EduWordMapVO getEduWordMapByEduIdAndWordsId(Long eduId, Long wordsId);

    // *학습별 단어 개수
    public int getEduWordMapCountByEduId(Long eduId);

    // *매핑 등록
    public void saveEduWordMap(EduWordMapVO eduWordMapVO);

    // 매핑 수정
    public void updateEduWordMap(EduWordMapVO eduWordMapVO);

    // ID 기준 삭제
    public void deleteEduWordMap(Long id);

    // 학습 + 단어 기준 삭제
    public void deleteEduWordMapByEduIdAndWordsId(Long eduId, Long wordsId);

    // OpenAPI 수어 데이터를 학습 단어로 등록
    public void saveEduWordFromSignWord(EduWordFromSignWordRequestDTO eduWordFromSignWordRequestDTO);
    }


