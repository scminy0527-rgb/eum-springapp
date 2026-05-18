package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.EduVideoResponseDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduVideoMapper {

    // 수어 영상 상세 조회
    public EduVideoResponseDTO select(Long id);
}
