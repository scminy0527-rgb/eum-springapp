package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "게시글 작성 요청 DTO")
public class PostRequestDTO {
    @Schema(description = "게시글 제목", example = "게시글 제목입니다.", required = true)
    private String postTitle;
    @Schema(description = "게시글 내용", example = "게시글 내용입니다.", required = true)
    private String postContent;
    @Schema(description = "게시글 태그", example = "자유게시판")
    private String postTag;
    @Schema(description = "게시글 첫 번째 이미지 URL (썸네일)", example = "https://bucket.url/image.jpg")
    private String postProfile;
//    @Schema(description = "유저 번호", example = "1", required = true)
//    private Long userId;
}
