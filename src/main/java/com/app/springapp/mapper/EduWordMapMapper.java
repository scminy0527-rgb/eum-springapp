package com.app.springapp.mapper;

import com.app.springapp.domain.vo.EduWordMapVO;
import org.apache.ibatis.annotations.Mapper;
import retrofit2.http.DELETE;

import java.util.List;

@Mapper
public interface EduWordMapMapper {

    // 전체 조회
    public List<EduWordMapVO> selectAll();

    // *학습별 매핑 조회
    public List<EduWordMapVO> selectByEduId(Long eduId);

    // 단어별 매핑 조회
    public List<EduWordMapVO> selectByWordsId(Long wordsId);

    // *학습 + 중복 확인용
    public EduWordMapVO selectByEduIdAndWordsId(Long eduId, Long wordsId);

    // *학습별 단어 개수
    public int countByEduId(Long eduId);

    // *매핑 등록
    public void insert(EduWordMapVO eduWordMapVO);

    // 매핑 수정
    public void update(EduWordMapVO eduWordMapVO);

    // ID 기준 삭제
    public void delete(Long id);

    // 학습 + 단어 기준 삭제
    public void deleteByEduIdAndWordsId(Long eduId, Long wordsId);

}
