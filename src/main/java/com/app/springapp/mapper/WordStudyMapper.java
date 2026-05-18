package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.WordStudyResponseDTO;
import com.app.springapp.domain.vo.WordStudyVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WordStudyMapper {

    // 단어 학습 기록 등록
    public void insert(WordStudyVO wordStudyVO);

    // 단어 학습 기록 조회
    public WordStudyResponseDTO select(Long userId, Long eduWordMapId);

    // 학습별 완료 단어 개수 조회
    public int countCompletedByEduId(Long userId, Long eduId);

    // 학습별 전체 단어 개수 조회
    public int countTotalByEduId(Long eduId);

    // 단어 학습 완료 상태 수정
    public void update(WordStudyVO wordStudyVO);

}
