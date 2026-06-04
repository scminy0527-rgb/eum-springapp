package com.app.springapp.service;

import com.app.springapp.domain.dto.response.EduCertResponseDTO;

import java.util.List;

public interface EduCertService {
    List<EduCertResponseDTO> getMyCerts(Long userId);
}
