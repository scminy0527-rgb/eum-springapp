package com.app.springapp.service;

import com.app.springapp.domain.dto.TestDTO;

import java.util.List;

public interface TestService {
    // 시험 목록 조회
    List<TestDTO> getTestList();
}
