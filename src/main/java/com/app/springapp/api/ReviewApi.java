package com.app.springapp.api;

import com.app.springapp.domain.dto.request.ReviewRequestDTO;
import com.app.springapp.domain.dto.response.ReviewResponseDTO;
import com.app.springapp.service.ReviewService;
import com.app.springapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewApi {

    private final ReviewService reviewService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/review")
    @Operation(summary = "후기 작성", description = "로그인한 유저가 수업 후기를 작성합니다.")
    @ApiResponse(responseCode = "200", description = "후기 작성 완료")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<?> writeReview(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @RequestBody ReviewRequestDTO request) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body("인증 실패");
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.valueOf(claims.get("id").toString());

        reviewService.writeReview(userId, request);
        return ResponseEntity.ok("후기 작성 완료");
    }

    @GetMapping("/reviews")
    @Operation(summary = "오늘의 후기 조회", description = "날짜 기반으로 매일 바뀌는 후기 목록을 조회합니다.")
    public ResponseEntity<List<ReviewResponseDTO>> getTodayReviews() {
        return ResponseEntity.ok(reviewService.getTodayReviews());
    }

    @GetMapping("/reviews/all")
    @Operation(summary = "후기 전체 조회", description = "후기 전체 페이지에 표시할 전체 목록을 조회합니다.")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }
}