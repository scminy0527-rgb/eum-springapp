package com.app.springapp.repository;

import com.app.springapp.domain.dto.TestDTO;
import com.app.springapp.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TestDAO {
    private final TestMapper testMapper;

    // 시험 목록 조회
    public List<TestDTO> findAll() {
        return testMapper.selectAll();
    }

    // 시험 단건 조회
    public Optional<TestDTO> findById(Long id) {
        return Optional.ofNullable(testMapper.select(id));
    }
}
