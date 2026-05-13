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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserApi {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
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
}
