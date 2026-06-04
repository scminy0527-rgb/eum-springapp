package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.EduCertResponseDTO;
import com.app.springapp.mapper.EduCertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EduCertDAO {
    private final EduCertMapper eduCertMapper;

    public List<EduCertResponseDTO> findByUserId(Long userId) {
        return eduCertMapper.selectByUserId(userId);
    }
}
