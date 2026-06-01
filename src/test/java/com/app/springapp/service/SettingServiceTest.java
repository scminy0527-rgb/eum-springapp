package com.app.springapp.service;

import com.app.springapp.domain.dto.request.SettingRequestDTO;
import com.app.springapp.domain.dto.response.SettingResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SettingServiceTest {

    @Autowired
    private SettingService settingService;

    //    회원 설정 조회 테스트
    @Test
    public void getSettingTest() {
        Long userId = 1L;

        SettingResponseDTO settingResponseDTO = settingService.getSetting(userId);

        log.info("회원 설정 조회 결과: {}", settingResponseDTO);
    }

    //    회원 설정 수정 테스트
    @Test
    public void updateSettingTest() {
        Long userId = 1L;

        SettingRequestDTO requestDTO = new SettingRequestDTO();
        requestDTO.setSettingReply(1);
        requestDTO.setSettingGood(1);
        requestDTO.setSettingBulletin(1);
        requestDTO.setSettingStudy(1);
        requestDTO.setSettingEmailPush(1);
        requestDTO.setSettingPostOpen(1);
        requestDTO.setSettingSignDefault(1);
        requestDTO.setSettingBrailleTranslate(1);
        requestDTO.setSettingAutoScroll(1);
        requestDTO.setSettingPushNotify(1);
        requestDTO.setSettingMentionNotify(1);

        settingService.updateSetting(requestDTO, userId);

        SettingResponseDTO settingResponseDTO = settingService.getSetting(userId);

        log.info("회원 설정 수정 결과: {}", settingResponseDTO);
    }
}