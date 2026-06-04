package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.EduStartResponseDTO;
import com.app.springapp.domain.vo.EduStartVO;
import com.app.springapp.mapper.EduStartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EduStartDAO {
    private final EduStartMapper eduStartMapper;

    // 사용자와 학습 번호로 시작 기록 조회
    public Optional<EduStartResponseDTO> findByUserIdAndEduId(Long userId, Long eduId) {
        return Optional.ofNullable(eduStartMapper.selectByUserIdAndEduId(userId, eduId));
    }

    // 학습 시작 기록 등록
    public void save(EduStartVO eduStartVO) {
        eduStartMapper.insert(eduStartVO);
    }
}
