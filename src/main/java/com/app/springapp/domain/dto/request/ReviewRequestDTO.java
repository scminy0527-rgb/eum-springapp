package com.app.springapp.domain.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ReviewRequestDTO {
        private int reviewRating;
        private List<String> reviewTags;
        private String reviewContent;
}