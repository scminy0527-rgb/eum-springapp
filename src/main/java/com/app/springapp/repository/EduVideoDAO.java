package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.EduVideoResponseDTO;
import com.app.springapp.mapper.EduVideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EduVideoDAO {
    private final EduVideoMapper eduVideoMapper;

    // 수어 영상 상세 조회
    public Optional<EduVideoResponseDTO> findVideoById(Long id) {
        return Optional.ofNullable(eduVideoMapper.select(id));
    }
}
