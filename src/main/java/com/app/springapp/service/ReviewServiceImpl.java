package com.app.springapp.service;

import com.app.springapp.domain.dto.ReviewDTO;
import com.app.springapp.domain.dto.request.ReviewRequestDTO;
import com.app.springapp.domain.dto.response.ReviewResponseDTO;
import com.app.springapp.repository.ReviewDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;

    @Override
    public void writeReview(Long userId, ReviewRequestDTO request) {
        ReviewDTO reviewDTO = new ReviewDTO(
                userId,
                request.getReviewRating(),
                String.join(",", request.getReviewTags()),
                request.getReviewContent()
        );
        reviewDAO.insertReview(reviewDTO);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewDAO.findAllReviews();
    }
}