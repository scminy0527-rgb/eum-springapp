package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "게시글 DTO")
public class PostDTO {
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
    @Schema(description = "작성자 유저 번호", example = "1")
    private Long userId;
    @Schema(description = "작성자 유저 닉네임", example = "수어러버박지민")
    private String userNickname;
    @Schema(description = "작성자 유저 프로필", example = "default.jpg")
    private String userProfile;
    @Schema(description = "게시글 좋아요 갯수", example = "3")
    private Long likeCount;
    @Schema(description = "게시글에 달린 갯수", example = "5")
    private Long commentCount;
    @Schema(description = "보고 있는 유저가 좋아요 누른 여부", example = "true")
    private Boolean isLiked;
    @Schema(description = "보고 있는 유저가 해당 게시글 작성자 여부", example = "true")
    private Boolean isOwner;
    @Schema(description = "해당 게시글 삭제 된 여부 (소프트 삭제)", example = "0")
    private Boolean postIsDeleted;
}
