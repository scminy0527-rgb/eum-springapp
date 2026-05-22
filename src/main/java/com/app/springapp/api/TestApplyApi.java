package com.app.springapp.api;

import com.app.springapp.domain.dto.TestApplyDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.TestApplyService;
import com.app.springapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-apply")
@Slf4j
public class TestApplyApi {

    private final TestApplyService testApplyService;
    private final JwtTokenUtil jwtTokenUtil;

    // 원서 접수
    @PostMapping
    @Operation(summary = "원서 접수")
    public ResponseEntity<ApiResponseDTO> apply(
            @RequestBody TestApplyDTO testApplyDTO,
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        // 인증 체크
        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        // 토큰에서 userId 추출
        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));
        testApplyDTO.setUserId(userId);

        // 원서 접수 처리 (정원 초과 시 예외 발생)
        testApplyService.apply(testApplyDTO);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "원서 접수가 완료되었습니다."));
    }
}
