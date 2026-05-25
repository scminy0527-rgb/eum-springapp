package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.PostDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "게시글 응답 DTO")
public class PostResponseDTO {
    @Schema(description = "게시글 번호", example = "1")
    private Long id;
    @Schema(description = "게시글 제목", example = "게시글 제목입니다.")
    private String postTitle;
    @Schema(description = "게시글 내용", example = "게시글 내용입니다.")
    private String postContent;
    @Schema(description = "조회수", example = "0")
    private int postReadCount;
    @Schema(description = "게시글 생성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime postCreateAt;
    @Schema(description = "게시글 태그", example = "자유게시판")
    private String postTag;
    @Schema(description = "게시글 프로필 이미지", example = "default.jpg")
    private String postProfile;
//    @Schema(description = "유저 번호", example = "1")
//    private Long userId;
    @Schema(description = "유저 닉네임", example = "수어러버박지민")
    private String userNickname;
    @Schema(description = "유저 프로필", example = "default.jpg")
    private String userProfile;
    @Schema(description = "게시글 좋아요 갯수", example = "3")
    private Long likeCount;
    @Schema(description = "게시글에 달린 갯수", example = "5")
    private Long commentCount;

    public static PostResponseDTO from(PostDTO dto) {
        PostResponseDTO res = new PostResponseDTO();
        res.setId(dto.getId());
        res.setPostTitle(dto.getPostTitle());
        res.setPostContent(dto.getPostContent());
        res.setPostReadCount(dto.getPostReadCount());
        res.setPostCreateAt(dto.getPostCreateAt());
        res.setPostTag(dto.getPostTag());
        res.setPostProfile(dto.getPostProfile());
//        res.setUserId(dto.getUserId());
        res.setUserNickname(dto.getUserNickname());
        res.setUserProfile(dto.getUserProfile());
        res.setLikeCount(dto.getLikeCount());
        res.setCommentCount(dto.getCommentCount());
        return res;
    }
}
