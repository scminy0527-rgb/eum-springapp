package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.SignWordResponseDTO;
import com.app.springapp.domain.vo.SignWordVO;

import java.util.List;

public interface SignWordService {
    // 수어 전체 조회
    public List<SignWordResponseDTO> getSignWords();

    // 수어 검색 조회 -> DB에서 검색
    public List<SignWordResponseDTO> getSignWordsByKeyword(String keyword);

    // 수어 상세 조회
    public SignWordResponseDTO getSignWordById(Long id);

    // 수어 등록
    public void saveSignWord(SignWordVO signWordVO);

    // 수어 수정
    public void updateSignWord(SignWordVO signWordVO);

    // 수어 삭제
    public void deleteSignWord(Long id);

    // OpenAPI 수어 데이터 동기화 -> OpenAPI에서 가져와 DB에 저장
    public int syncSignWords(int pageNo, int numOfRows);

    // 오늘의 수어 영상 3개 (날짜 기반)
    public List<SignWordResponseDTO> getTodaySignWords();
}
