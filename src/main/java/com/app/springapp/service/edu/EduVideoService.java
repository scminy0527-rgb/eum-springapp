package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.EduVideoResponseDTO;

public interface EduVideoService {

    // 수어 영상 상세 조회
    public EduVideoResponseDTO getEduVideoById(Long id);
}
