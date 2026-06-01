package com.app.springapp.service;

import com.app.springapp.domain.dto.request.SettingRequestDTO;
import com.app.springapp.domain.dto.response.SettingResponseDTO;

public interface SettingService {

    //    회원 설정 조회
    SettingResponseDTO getSetting(Long userId);

    //    회원 설정 수정
    void updateSetting(SettingRequestDTO requestDTO, Long userId);
}