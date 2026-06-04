package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.EduStartResponseDTO;
import com.app.springapp.domain.vo.EduStartVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduStartMapper {

    // 사용자와 학습 번호로 시작 기록 조회
    public EduStartResponseDTO selectByUserIdAndEduId(Long userId, Long eduId);

    // 학습 시작 기록 등록
    public void insert(EduStartVO eduStartVO);

}