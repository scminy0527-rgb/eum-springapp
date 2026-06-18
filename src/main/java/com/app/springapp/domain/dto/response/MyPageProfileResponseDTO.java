package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "마이페이지 프로필 응답 DTO")
public class MyPageProfileResponseDTO {
    @Schema(description = "회원 번호", example = "1", required = true)
    private Long id;

    @Schema(description = "회원 이름", example = "김민준", required = true)
    private String userName;

    @Schema(description = "회원 닉네임", example = "minjun_k", required = true)
    private String userNickname;

    @Schema(description = "회원 자기소개", example = "수어를 배우고 청각장애인 친구들과 소통하고 싶어요!", required = false)
    private String userIntro;

    @Schema(description = "회원 직업", example = "학생", required = true)
    private String userJob;

    @Schema(description = "회원 지역", example = "서울특별시 강남구", required = true)
    private String userAddress;

    @Schema(description = "회원 이메일", example = "minginew77@gmail.com", required = true)
    private String userEmail;

    @Schema(description = "회원 전화번호", example = "01012345678", required = false)
    private String userPhoneNum;

    @Schema(description = "회원 총 경험치", example = "120", required = true)
    private Long userExp;

    @Schema(description = "현재 레벨", example = "2", required = true)
    private Integer userLevel;

    @Schema(description = "현재 레벨 이름", example = "입문자", required = true)
    private String userLevelName;

    @Schema(description = "현재 레벨에서 획득한 경험치", example = "20", required = true)
    private Long currentLevelExp;

    @Schema(description = "다음 레벨까지 필요한 경험치", example = "120", required = true)
    private Long nextLevelExp;

    @Schema(description = "다음 레벨까지 남은 경험치", example = "100", required = true)
    private Long remainingExp;

    @Schema(description = "현재 레벨 경험치 진행률", example = "16", required = true)
    private Integer expPercent;

    @Schema(description = "회원 프로필 이미지", example = "/2026/05/27/example.png", required = false)
    private String userProfile;

    @Schema(description = "회원 가입일", example = "2026-05-27T04:09:09", required = true)
    private LocalDateTime userCreateAt;

    @Schema(description = "회원 권한", example = "USER", required = false)
    private String userRole;

    @Schema(description = "소셜 로그인 회원 여부", example = "true", required = true)
    private Boolean socialUser;

    @Schema(description = "소셜 로그인 제공자", example = "GOOGLE", required = false)
    private String socialMemberProvider;

    @Schema(description = "마지막 이메일 변경일", example = "2026-06-16T10:00:00", required = false)
    private LocalDateTime userEmailChangedAt;
}