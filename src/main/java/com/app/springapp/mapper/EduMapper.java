package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.EduResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduMapper {

    // 학습 목록 조회
    public List<EduResponseDTO> selectAll();

    // 학습 상세 조회
    public EduResponseDTO select(Long id);
}
