package com.app.springapp.repository;

import com.app.springapp.domain.dto.SettingDTO;
import com.app.springapp.domain.dto.request.SettingRequestDTO;
import com.app.springapp.domain.vo.SettingVO;
import com.app.springapp.mapper.SettingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SettingDAO {

    private final SettingMapper settingMapper;

    //    회원 설정 조회
    public Optional<SettingDTO> findByUserId(Long userId) {
        return Optional.ofNullable(settingMapper.selectByUserId(userId));
    }

    //    회원 기본 설정 생성
    public void saveDefault(SettingVO settingVO) {
        settingMapper.insertDefault(settingVO);
    }

    //    회원 설정 수정
    public void updateByUserId(SettingRequestDTO requestDTO, Long userId) {
        settingMapper.updateByUserId(requestDTO, userId);
    }
}