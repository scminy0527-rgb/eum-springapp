package com.app.springapp.service;

import com.app.springapp.domain.dto.response.EduCertResponseDTO;
import com.app.springapp.repository.EduCertDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EduCertServiceImpl implements EduCertService {
    private final EduCertDAO eduCertDAO;

    @Override
    public List<EduCertResponseDTO> getMyCerts(Long userId) {
        List<EduCertResponseDTO> list = eduCertDAO.findByUserId(userId);
        list.forEach(cert -> {
            if (cert.getEduCertCreateAt() != null) {
                cert.setEduCertExpireAt(cert.getEduCertCreateAt().plusDays(180));
            }
        });
        return list;
    }
}
