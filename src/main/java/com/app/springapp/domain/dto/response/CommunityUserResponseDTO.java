package com.app.springapp.domain.dto.response;

import com.app.springapp.domain.dto.CommunityUserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "커뮤니티 유저 프로필 정보 응답 DTO")
public class CommunityUserResponseDTO {
    @Schema(description = "유저 번호", example = "1")
    private Long id;
    @Schema(description = "유저 닉네임", example = "길동이")
    private String userNickname;
    @Schema(description = "유저 소개", example = "안녕하세요!")
    private String userIntro;
    @Schema(description = "유저 직업", example = "개발자")
    private String userJob;
    @Schema(description = "유저 이메일", example = "user@example.com")
    private String userEmail;
    @Schema(description = "유저 경험치", example = "0")
    private int userExp;
    @Schema(description = "유저 프로필 이미지", example = "default.jpg")
    private String userProfile;
    @Schema(description = "유저 생성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime userCreateAt;
    @Schema(description = "유저 작성 게시글 갯수", example = "1")
    private int postCount;
    @Schema(description = "유저가 좋아요 한 게시글 갯수", example = "1")
    private int postLikeCount;
    @Schema(description = "유저 작성한 댓글 수", example = "1")
    private int commentCount;
    @Schema(description = "유저가 받은 좋아요 갯수", example = "1")
    private int getLikeCount;

    public static CommunityUserResponseDTO from (CommunityUserDTO communityUserDTO) {
        CommunityUserResponseDTO communityUserResponseDTO = new CommunityUserResponseDTO();
        communityUserResponseDTO.setId(communityUserDTO.getId());
        communityUserResponseDTO.setUserNickname(communityUserDTO.getUserNickname());
        communityUserResponseDTO.setUserIntro(communityUserDTO.getUserIntro());
        communityUserResponseDTO.setUserJob(communityUserDTO.getUserJob());
        communityUserResponseDTO.setUserEmail(communityUserDTO.getUserEmail());
        communityUserResponseDTO.setUserExp(communityUserDTO.getUserExp());
        communityUserResponseDTO.setUserProfile(communityUserDTO.getUserProfile());
        communityUserResponseDTO.setUserCreateAt(communityUserDTO.getUserCreateAt());
        communityUserResponseDTO.setPostCount(communityUserDTO.getPostCount());
        communityUserResponseDTO.setPostLikeCount(communityUserDTO.getPostLikeCount());
        communityUserResponseDTO.setCommentCount(communityUserDTO.getCommentCount());
        communityUserResponseDTO.setGetLikeCount(communityUserDTO.getGetLikeCount());
        return communityUserResponseDTO;
    }
}
