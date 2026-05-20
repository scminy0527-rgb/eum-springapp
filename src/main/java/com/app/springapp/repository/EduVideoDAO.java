package com.app.springapp.repository;

import com.app.springapp.domain.dto.response.EduVideoResponseDTO;
import com.app.springapp.domain.vo.EduVideoVO;
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

    // 관리자용
    // 수어 영상 등록
    public void save(EduVideoVO eduVideoVO) {
        eduVideoMapper.insert(eduVideoVO);
    }

    // 수어 영상 수정
    public void update(EduVideoVO eduVideoVO) {
        eduVideoMapper.update(eduVideoVO);
    }
}
