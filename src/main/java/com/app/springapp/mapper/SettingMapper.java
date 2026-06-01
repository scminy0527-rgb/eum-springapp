package com.app.springapp.mapper;

import com.app.springapp.domain.dto.SettingDTO;
import com.app.springapp.domain.dto.request.SettingRequestDTO;
import com.app.springapp.domain.vo.SettingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper {

    //    회원 설정 조회
    SettingDTO selectByUserId(Long userId);

    //    회원 기본 설정 생성
    void insertDefault(SettingVO settingVO);

    //    회원 설정 수정
    void updateByUserId(
            @Param("requestDTO") SettingRequestDTO requestDTO,
            @Param("userId") Long userId
    );
}