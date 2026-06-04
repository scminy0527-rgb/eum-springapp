package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.EduCertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/api/edu-certs")
public class EduCertApi {
    private final EduCertService eduCertService;

    @GetMapping("/me")
    @Operation(summary = "내 수료증 목록 조회", description = "로그인한 회원의 수료증 목록(발급일·만료일·과정명)을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "수료증 목록 조회 성공")
    @ApiResponse(responseCode = "401", description = "토큰 없음 또는 만료")
    public ResponseEntity<ApiResponseDTO> getMyCerts(Authentication authentication) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        return ResponseEntity.ok(
                ApiResponseDTO.of(true, "수료증 목록 조회 성공", eduCertService.getMyCerts(userDTO.getId()))
        );
    }
}
