package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ReviewRequestDTO;
import com.app.springapp.domain.dto.response.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    void writeReview(Long userId, ReviewRequestDTO request);
    List<ReviewResponseDTO> getAllReviews();
}