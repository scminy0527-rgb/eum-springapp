package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.EduResponseDTO;
import com.app.springapp.domain.vo.EduVO;
import com.app.springapp.mapper.EduMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EduDAO {
    private final EduMapper eduMapper;

    // 학습 목록 조회
    public List<EduResponseDTO> findAll() {
        return eduMapper.selectAll();
    }

    // 학습 상세 조회
    public Optional<EduResponseDTO> findByEduId(Long id) {
        return Optional.ofNullable(eduMapper.select(id));
    }

    // 관리자용
    // 학습 등록
    public void save(EduVO eduVO) {
        eduMapper.insert(eduVO);
    }

    // 학습 수정
    public void update(EduVO eduVO) {
        eduMapper.update(eduVO);
    }

    // 학습 삭제 처리
    public void delete(Long id) {
        eduMapper.delete(id);
    }

    // 삭제된 학습 목록 조회
    public List<EduResponseDTO> findDeletedList() {
        return eduMapper.selectDeletedAll();
    }

    // 학습 복구 처리
    public void restore(Long id) {
        eduMapper.restore(id);
    }
}
