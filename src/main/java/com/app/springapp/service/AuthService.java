package com.app.springapp.service;

import com.app.springapp.domain.dto.JwtTokenDTO;
import com.app.springapp.domain.dto.UserDTO;

public interface AuthService {
    // 로컬 로그인
    public JwtTokenDTO login(UserDTO userDTO);

    // 소셜 로그인 (기존 회원)
    public JwtTokenDTO socialLogin(UserDTO userDTO);

    // 소셜 신규 회원 여부 확인
    public boolean isSocialUserExists(UserDTO userDTO);

    // 소셜 신규 회원용 임시 토큰 발급
    public String generateTempSocialToken(UserDTO userDTO);

    // 소셜 회원가입 완료 (임시 토큰 검증 후 계정 생성)
    public JwtTokenDTO socialSignup(UserDTO userDTO, String tempToken);

    // 로그아웃
    public void logout(JwtTokenDTO jwtTokenDTO);

    // Redis에 refresh Token 저장
    public boolean saveRefreshToken(JwtTokenDTO jwtTokenDTO);

    // Redis에 저장된 refresh Token이 유효한지 검증
    public boolean validateRefreshToken(JwtTokenDTO jwtTokenDTO);

    // Redis에 블랙리스트를 등록 (로그아웃 시 토큰 무효화)
    public boolean saveBlackListedToken(JwtTokenDTO jwtTokenDTO);

    // Redis에 등록된 블랙리스트인지 검증
    public boolean isBlackListedToken(JwtTokenDTO jwtTokenDTO);

    // refresh 토큰을 검증하고, 새로운 accessToken 발급 서비스
    public JwtTokenDTO reissueAccessToken(JwtTokenDTO jwtTokenDTO);

    // 휴대폰 인증 코드 발송
    public boolean sendUserPhoneVerificationCode(String userPhone);

    // 휴대폰 인증 코드 검증
    public boolean verifyUserPhoneVerificationCode(String userPhone, String code);

    // 이메일 인증 코드 발송
    public boolean sendUserEmailVerificationCode(String userEmail);

    // 이메일 인증 코드 검증
    public boolean verifyUserEmailVerificationCode(String userEmail, String code);
}
