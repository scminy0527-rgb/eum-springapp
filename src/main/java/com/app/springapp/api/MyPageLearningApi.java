package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.MyPageLearningService;
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
@RequestMapping("/private/api/mypage/learning")
public class MyPageLearningApi {

    private final MyPageLearningService myPageLearningService;

    // 마이페이지 학습 페이지 조회
    @GetMapping("")
    @Operation(summary = "마이페이지 학습 페이지 조회", description = "로그인한 회원의 학습현황, 학습결과, 학습요약 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "마이페이지 학습 페이지 조회 성공")
    public ResponseEntity<ApiResponseDTO> getLearning(Authentication authentication) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponseDTO.of(true, "마이페이지 학습 페이지 조회 성공", myPageLearningService.getLearning(userDTO.getId()))
        );
    }

    // AI 학습 분석 조회
    @GetMapping("/analysis")
    @Operation(summary = "AI 학습 분석 조회", description = "로그인한 회원의 학습 결과를 AI로 분석합니다.")
    @ApiResponse(responseCode = "200", description = "AI 학습 분석 조회 성공")
    public ResponseEntity<ApiResponseDTO> getLearningAnalysis(Authentication authentication) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        return ResponseEntity.ok(
                ApiResponseDTO.of(true, "AI 학습 분석 조회 성공", myPageLearningService.getLearningAnalysis(userDTO.getId()))
        );
    }
}