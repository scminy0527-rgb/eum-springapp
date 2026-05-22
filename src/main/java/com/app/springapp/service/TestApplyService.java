package com.app.springapp.service;

import com.app.springapp.domain.dto.TestApplyDTO;

public interface TestApplyService {
    // 원서 접수 (정원 초과 시 예외 발생)
    void apply(TestApplyDTO testApplyDTO);
}
