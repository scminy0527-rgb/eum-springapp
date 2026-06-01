package com.app.springapp.mapper;

import com.app.springapp.domain.dto.ReviewDTO;
import com.app.springapp.domain.dto.request.ReviewRequestDTO;
import com.app.springapp.domain.dto.response.ReviewResponseDTO;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void insertReview(ReviewDTO reviewDTO);
    List<ReviewResponseDTO> selectAllReviews();
}