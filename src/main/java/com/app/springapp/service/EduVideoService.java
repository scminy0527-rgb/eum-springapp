package com.app.springapp.service;

import com.app.springapp.domain.dto.response.EduVideoResponseDTO;
import com.app.springapp.domain.vo.EduVideoVO;

public interface EduVideoService {

    // 수어 영상 상세 조회
    public EduVideoResponseDTO getEduVideoById(Long id);

    // 관리자용
    // 수어 영상 등록
    public void saveEduVideo(EduVideoVO eduVideoVO);

    // 수어 영상 수정
    public void updateEduVideo(EduVideoVO eduVideoVO);

}
