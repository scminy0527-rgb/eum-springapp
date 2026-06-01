package com.app.springapp.repository;

import com.app.springapp.domain.dto.ReviewDTO;
import com.app.springapp.domain.dto.request.ReviewRequestDTO;
import com.app.springapp.domain.dto.response.ReviewResponseDTO;
import com.app.springapp.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewDAO {

    private final ReviewMapper reviewMapper;

    public void insertReview(ReviewDTO reviewDTO) {
        reviewMapper.insertReview(reviewDTO);
    }

    public List<ReviewResponseDTO> findAllReviews() {
        return reviewMapper.selectAllReviews();
    }
}