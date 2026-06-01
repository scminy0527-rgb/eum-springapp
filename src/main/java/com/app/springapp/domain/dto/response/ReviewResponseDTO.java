package com.app.springapp.domain.dto.response;

import lombok.Data;

@Data
public class ReviewResponseDTO {
        private Long reviewId;
        private String userNickname;
        private String userProfile;
        private String userJob;
        private int reviewRating;
        private String reviewTags;
        private String reviewContent;
        private String reviewDate;
}
