package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserApi {

    private final UserService userService;

    // 회원가입
    @PostMapping
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 이름으로 로컬 회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @ApiResponse(responseCode = "400", description = "중복된 이메일")
    public ResponseEntity<ApiResponseDTO> join(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.join(userDTO));
    }

    // 내 정보 조회 (/private/** → JwtAuthenticationFilter 적용)
    @GetMapping("/private/me")
    @Operation(summary = "내 정보 조회", description = "Access Token으로 내 유저 정보 조회")
    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공")
    @ApiResponse(responseCode = "401", description = "토큰 없음 또는 만료")
    public ResponseEntity<ApiResponseDTO> me(Authentication authentication) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        return ResponseEntity.ok(userService.me(userDTO.getId()));
    }

    // 이메일 찾기
    @GetMapping("/email")
    @Operation(summary = "이메일 찾기", description = "이름으로 가입된 이메일 조회")
    public ResponseEntity<ApiResponseDTO> findEmail(@RequestParam String userName) {
        return ResponseEntity.ok(userService.findEmail(userName));
    }

    // 비밀번호 재설정
    @PatchMapping("/password")
    @Operation(summary = "비밀번호 재설정", description = "이메일로 비밀번호 변경")
    @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    @ApiResponse(responseCode = "400", description = "새 비밀번호 누락")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 이메일")
    public ResponseEntity<ApiResponseDTO> resetPassword(@RequestBody Map<String, String> body) {
        String userEmail = body.get("userEmail");
        String newPassword = body.get("newPassword");
        return ResponseEntity.ok(userService.resetPassword(userEmail, newPassword));
    }
}
