package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.vo.UserVO;
import com.app.springapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserPrivateApi {

    private final UserService userService;

    // 내 정보 조회 (토큰 필요)
    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "Access Token으로 내 유저 정보 조회")
    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공")
    @ApiResponse(responseCode = "401", description = "토큰 없음 또는 만료")
    public ResponseEntity<ApiResponseDTO> me(Authentication authentication) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        return ResponseEntity.ok(userService.me(userDTO.getId()));
    }

    // 프로필 사진 변경 (토큰 필요)
    @PostMapping("/profile-update")
    @Operation(summary = "프로필 사진 변경", description = "Access Token으로 인증 후 프로필 사진 URL 변경")
    @ApiResponse(responseCode = "200", description = "프로필 사진 변경 성공")
    @ApiResponse(responseCode = "401", description = "토큰 없음 또는 만료")
    public ResponseEntity<ApiResponseDTO> profileUpdate(
            Authentication authentication,
            @RequestBody UserVO userVO) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        userVO.setId(userDTO.getId());
        return ResponseEntity.ok(userService.updatePicture(userVO));
    }
}
