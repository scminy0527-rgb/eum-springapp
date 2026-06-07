package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ReviewRequestDTO;
import com.app.springapp.domain.dto.response.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    public void writeReview(Long userId, ReviewRequestDTO request);
    public List<ReviewResponseDTO> getAllReviews();
    public List<ReviewResponseDTO> getTodayReviews();
    public void clearTodayReviewsCache();
}