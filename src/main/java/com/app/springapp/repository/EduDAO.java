package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.EduResponseDTO;
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
}
