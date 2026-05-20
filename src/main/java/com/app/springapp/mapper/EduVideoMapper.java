package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.EduVideoResponseDTO;
import com.app.springapp.domain.vo.EduVideoVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduVideoMapper {

    // 수어 영상 상세 조회
    public EduVideoResponseDTO select(Long id);

    // 관리자용
    // 수어 영상 등록
    public void insert(EduVideoVO eduVideoVO);

    // 수어 영상 수정
    public void update(EduVideoVO eduVideoVO);
}
