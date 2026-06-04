package com.app.springapp.domain.dto;

import com.app.springapp.domain.enums.SocialProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "커뮤니티 프로필 유저 DTO")
public class CommunityUserDTO {
    @Schema(description = "유저 번호", example = "1")
    private Long id;
    @Schema(description = "유저 이름", example = "홍길동")
    private String userName;
    @Schema(description = "유저 닉네임", example = "길동이")
    private String userNickname;
    @Schema(description = "유저 소개", example = "안녕하세요!")
    private String userIntro;
    @Schema(description = "유저 직업", example = "개발자")
    private String userJob;
    @Schema(description = "유저 주소", example = "서울특별시 강남구")
    private String userAddress;
    @Schema(description = "유저 이메일", example = "user@example.com")
    private String userEmail;
    @Schema(description = "유저 전화번호", example = "010-1234-5678")
    private String userPhoneNum;
    @Schema(description = "유저 비밀번호", example = "password123!")
    private String userPassword;
    @Schema(description = "유저 경험치", example = "0")
    private int userExp;
    @Schema(description = "유저 프로필 이미지", example = "default.jpg")
    private String userProfile;
    @Schema(description = "유저 생성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime userCreateAt;
    @Schema(description = "소셜 로그인 제공 ID")
    private String socialUserProviderId;
    @Schema(description = "소셜 로그인(google/naver/kakao/local)", example = "local")
    private SocialProvider socialUserProvider;
    @Schema(description = "유저 생년월일", example = "1990-01-01")
    private String userBirth;
    @Schema(description = "유저 권한", example = "user")
    private String userRole;
    @Schema(description = "자동 로그인 여부", example = "false")
    private boolean autoLogin;
    @Schema(description = "유저 작성 게시글 갯수", example = "1")
    private int postCount;
    @Schema(description = "유저가 좋아요 한 게시글 갯수", example = "1")
    private int postLikeCount;
    @Schema(description = "유저 작성한 댓글 수", example = "1")
    private int commentCount;
    @Schema(description = "유저가 받은 좋아요 갯수", example = "1")
    private int getLikeCount;
}
