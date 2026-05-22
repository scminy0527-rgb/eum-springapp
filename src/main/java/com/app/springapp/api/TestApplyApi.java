package com.app.springapp.api;

import com.app.springapp.domain.dto.TestApplyDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.TestApplyService;
import com.app.springapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-apply")
@Slf4j
public class TestApplyApi {

    private final TestApplyService testApplyService;
    private final JwtTokenUtil jwtTokenUtil;

    // 원서 접수
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "원서 접수")
    public ResponseEntity<ApiResponseDTO> apply(
            @RequestPart("testId") String testId,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));

        TestApplyDTO testApplyDTO = new TestApplyDTO();
        testApplyDTO.setTestId(Long.parseLong(testId));
        testApplyDTO.setUserId(userId);

        testApplyService.apply(testApplyDTO, files);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "원서 접수가 완료되었습니다."));
    }

    // 접수 취소
    @DeleteMapping("/{id}")
    @Operation(summary = "접수 취소")
    public ResponseEntity<ApiResponseDTO> cancel(
            @PathVariable Long id,
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));

        testApplyService.cancel(id, userId);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "접수가 취소되었습니다."));
    }

    // 내 접수 목록 조회
    @GetMapping("/my")
    @Operation(summary = "내 접수 목록 조회")
    public ResponseEntity<ApiResponseDTO> getMyApplyList(
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));

        List<TestApplyDTO> list = testApplyService.getMyApplyList(userId);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "조회 성공", list));
    }
}
