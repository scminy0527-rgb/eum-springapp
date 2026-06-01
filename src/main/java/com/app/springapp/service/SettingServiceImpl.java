package com.app.springapp.service;

import com.app.springapp.domain.dto.SettingDTO;
import com.app.springapp.domain.dto.request.SettingRequestDTO;
import com.app.springapp.domain.dto.response.SettingResponseDTO;
import com.app.springapp.domain.vo.SettingVO;
import com.app.springapp.repository.SettingDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final SettingDAO settingDAO;

    //    회원 설정 조회
    @Override
    public SettingResponseDTO getSetting(Long userId) {
        SettingDTO settingDTO = settingDAO.findByUserId(userId)
                .orElseGet(() -> createDefaultSetting(userId));

        return SettingResponseDTO.from(settingDTO);
    }

    //    회원 설정 수정
    @Override
    public void updateSetting(SettingRequestDTO requestDTO, Long userId) {
        getSetting(userId);
        settingDAO.updateByUserId(requestDTO, userId);
    }

    //    설정 정보가 없으면 기본 설정을 생성 후 다시 조회
    private SettingDTO createDefaultSetting(Long userId) {
        SettingVO settingVO = new SettingVO();

        settingVO.setUserId(userId);
        settingDAO.saveDefault(settingVO);

        return settingDAO.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("기본 설정 생성에 실패했습니다."));
    }
}