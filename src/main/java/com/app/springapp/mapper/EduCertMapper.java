package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.EduCertResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EduCertMapper {
    List<EduCertResponseDTO> selectByUserId(@Param("userId") Long userId);
}
