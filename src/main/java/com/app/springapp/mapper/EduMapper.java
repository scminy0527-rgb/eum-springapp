package com.app.springapp.mapper;

import com.app.springapp.domain.dto.response.EduResponseDTO;
import com.app.springapp.domain.vo.EduVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EduMapper {

    // 학습 목록 조회
    public List<EduResponseDTO> selectAll();

    // 학습 상세 조회
    public EduResponseDTO select(Long id);

    // 관리자용
    // 학습 등록
    public void insert(EduVO eduVO);

    // 학습 수정
    public void update(EduVO eduVO);

    // 학습 삭제 처리
    public void delete(Long id);

    // 삭제된 학습 목록 조회
    public List<EduResponseDTO> selectDeletedAll();

    // 학습 복구 처리
    public void restore(Long id);
}
