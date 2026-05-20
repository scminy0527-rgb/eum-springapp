package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.SignWordResponseDTO;

import java.util.List;

public interface SignWordService {

    // 문화공공데이터 수어 검색
    public List<SignWordResponseDTO> searchSignWords(String keyword, int pageNo, int numOfRows);
}
