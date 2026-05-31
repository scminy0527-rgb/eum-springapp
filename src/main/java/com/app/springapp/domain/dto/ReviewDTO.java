package com.app.springapp.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewDTO {
    private Long userId;
    private int reviewRating;
    private String reviewTags;
    private String reviewContent;
}
