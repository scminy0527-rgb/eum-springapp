package com.app.springapp.api;

import com.app.springapp.domain.dto.CertRenewDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.CertRenewService;
import com.app.springapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cert-renew")
public class CertRenewApi {

    private final CertRenewService certRenewService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    @Operation(summary = "자격증 갱신/재발급 신청")
    public ResponseEntity<ApiResponseDTO> apply(
            @RequestBody CertRenewDTO certRenewDTO,
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "로그인이 필요합니다."));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));
        certRenewDTO.setUserId(userId);

        certRenewService.apply(certRenewDTO);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "신청이 완료되었습니다."));
    }

    @GetMapping
    @Operation(summary = "내 갱신/재발급 신청 내역 조회")
    public ResponseEntity<ApiResponseDTO> getMyApplications(
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "로그인이 필요합니다."));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));

        List<CertRenewDTO> list = certRenewService.getMyApplications(userId);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "조회 성공", list));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "갱신/재발급 신청 취소")
    public ResponseEntity<ApiResponseDTO> cancel(
            @PathVariable Long id,
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "로그인이 필요합니다."));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));

        certRenewService.cancel(id, userId);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "취소되었습니다."));
    }
}
