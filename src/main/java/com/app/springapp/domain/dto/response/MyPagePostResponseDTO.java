package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "마이페이지 게시글 응답 DTO")
public class MyPagePostResponseDTO {
    @Schema(description = "게시글 번호", example = "1", required = true)
    private Long id;

    @Schema(description = "게시글 제목", example = "수어 질문 있습니다", required = true)
    private String postTitle;

    @Schema(description = "게시글 태그", example = "자유게시판", required = true)
    private String postTag;

    @Schema(description = "조회 수", example = "150", required = true)
    private Long postReadCount;

    @Schema(description = "댓글 수", example = "3", required = true)
    private Long commentCount;

    @Schema(description = "좋아요 수", example = "5", required = true)
    private Long likeCount;

    @Schema(description = "게시글 작성일", example = "2026-05-25T16:40:44", required = true)
    private LocalDateTime postCreateAt;

    @Schema(description = "작성자 이름", example = "김민준", required = false)
    private String userName;

    @Schema(description = "작성자 닉네임", example = "minjun_k", required = false)
    private String userNickname;
}