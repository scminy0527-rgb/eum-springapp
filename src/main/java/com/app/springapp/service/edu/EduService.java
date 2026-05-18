package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.EduResponseDTO;

import java.util.List;

public interface EduService {

    // 학습 목록 조회
    public List<EduResponseDTO> getEduList();

    // 학습 상세 조회
    public EduResponseDTO getEduDetailById(Long id);
}
