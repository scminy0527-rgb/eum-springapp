package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.EduVideoResponseDTO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.EduVideoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class EduVideoServiceImpl implements EduVideoService {
    private final EduVideoDAO eduVideoDAO;

    // 수어 영상 상세 조회
    @Override
    public EduVideoResponseDTO getEduVideoById(Long id) {
        return eduVideoDAO.findVideoById(id).orElseThrow(() -> new EduException("수어 영상 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}
