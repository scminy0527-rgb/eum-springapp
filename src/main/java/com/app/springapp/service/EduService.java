package com.app.springapp.service;

import com.app.springapp.domain.dto.response.EduResponseDTO;
import com.app.springapp.domain.vo.EduVO;

import java.util.List;

public interface EduService {

    // 학습 목록 조회
    public List<EduResponseDTO> getEduList();

    // 학습 상세 조회
    public EduResponseDTO getEduDetailById(Long id);

    // 관리자용
    // 학습 등록
    public void saveEdu(EduVO eduVO);

    // 학습 수정
    public void updateEdu(EduVO eduVO);

    // 학습 삭제 처리
    public void deleteEdu(Long id);

    // 삭제된 학습 목록 조회
    public List<EduResponseDTO> getDeletedEduList();

    // 학습 복구 처리
    public void restoreEdu(Long id);
}
