package com.app.springapp.service;

import com.app.springapp.domain.dto.response.MyPageLearningResponseDTO;

public interface MyPageLearningService {
    //    마이페이지 학습 페이지 조회
    MyPageLearningResponseDTO getLearning(Long userId);
}