package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.EduResponseDTO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.EduDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class EduServiceImpl implements EduService {
    private final EduDAO eduDAO;

    // 학습 목록 조회
    @Override
    public List<EduResponseDTO> getEduList() {
        return eduDAO.findAll();
    }

    // 학습 상세 조회
    @Override
    public EduResponseDTO getEduDetailById(Long id) {
        return eduDAO.findByEduId(id).orElseThrow(() -> new EduException("학습 정보를 찾을 수 없습니다", HttpStatus.NOT_FOUND));
    }
}
