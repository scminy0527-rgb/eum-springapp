package com.app.springapp.api;

import com.app.springapp.domain.dto.JwtTokenDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.AuthService;
import com.app.springapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthApi {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일 + 비밀번호로 로그인 후 JWT 쿠키 발급")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "400", description = "이메일 또는 비밀번호 불일치")
    public ResponseEntity<ApiResponseDTO> login(@RequestBody UserDTO userDTO) {

        JwtTokenDTO jwtTokenDTO = authService.login(userDTO);

        // Access Token 쿠키
        ResponseCookie accessTokenCookie = ResponseCookie
                .from("accessToken", jwtTokenDTO.getAccessToken())
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .secure(false)              // 배포 환경에서는 true
                .maxAge(60 * 60 * 24)       // 24시간
                .build();

        // Refresh Token 쿠키
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from("refreshToken", jwtTokenDTO.getRefreshToken())
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .secure(false)
                .maxAge(60 * 60 * 24 * 30)  // 30일
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(ApiResponseDTO.of(true, "로그인 성공"));
    }

    // Access Token 재발급
    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "Refresh Token으로 새로운 Access Token 발급")
    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공")
    @ApiResponse(responseCode = "400", description = "유효하지 않은 Refresh Token")
    public ResponseEntity<ApiResponseDTO> reissue(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
        jwtTokenDTO.setAccessToken(accessToken);
        jwtTokenDTO.setRefreshToken(refreshToken);

        JwtTokenDTO reissued = authService.reissueAccessToken(jwtTokenDTO);

        ResponseCookie newAccessTokenCookie = ResponseCookie
                .from("accessToken", reissued.getAccessToken())
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .secure(false)
                .maxAge(60 * 60 * 24)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, newAccessTokenCookie.toString())
                .body(ApiResponseDTO.of(true, "토큰 재발급 성공"));
    }

    @GetMapping("/check")
    @Operation(summary = "인증 확인", description = "Access Token 유효성 확인")
    @ApiResponse(responseCode = "200", description = "인증 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ApiResponseDTO> check(
            @CookieValue(name = "accessToken", required = false) String accessToken) {
        if (accessToken == null) {
            return ResponseEntity.status(401)
                    .body(ApiResponseDTO.of(false, "인증 실패"));
        }
        return ResponseEntity.ok()
                .body(ApiResponseDTO.of(true, "인증 성공"));
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "토큰에서 유저 정보 반환")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<?> getMe(
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401)
                    .body(ApiResponseDTO.of(false, "인증 실패"));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);

        return ResponseEntity.ok(Map.of(
                "id", claims.get("id"),
                "userEmail", claims.get("userEmail"),
                "role", claims.getOrDefault("role", "USER")  // ← null 대신 기본값
        ));
    }
}
