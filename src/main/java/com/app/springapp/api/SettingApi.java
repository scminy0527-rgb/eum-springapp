package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.SettingRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/api/mypage/setting")
public class SettingApi {

    private final SettingService settingService;

    //    회원 설정 조회
    @GetMapping("")
    @Operation(summary = "회원 설정 조회", description = "로그인한 회원의 설정 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "회원 설정 조회 성공")
    public ResponseEntity<ApiResponseDTO> getSetting(Authentication authentication) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponseDTO.of(true, "회원 설정 조회 성공", settingService.getSetting(userDTO.getId()))
        );
    }

    //    회원 설정 수정
    @PatchMapping("")
    @Operation(summary = "회원 설정 수정", description = "로그인한 회원의 설정 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "회원 설정 수정 성공")
    public ResponseEntity<ApiResponseDTO> updateSetting(
            @RequestBody SettingRequestDTO requestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        settingService.updateSetting(requestDTO, userDTO.getId());

        return ResponseEntity.ok(
                ApiResponseDTO.of(true, "회원 설정 수정 성공")
        );
    }
}