package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "유저 DTO")
public class UserDTO {
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
    private String socialUserProvider;
    @Schema(description = "유저 권한", example = "user")
    private String userRole;


    //    소셜 로그인 제공자의 초기값
    {
        socialUserProvider = "local";
        userRole = "user";
    }
}
