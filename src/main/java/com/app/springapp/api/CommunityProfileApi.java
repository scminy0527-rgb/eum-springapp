package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.CommunityUserResponseDTO;
import com.app.springapp.domain.dto.response.UserResponseDTO;
import com.app.springapp.service.CommunityProfileService;
import com.app.springapp.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/community-profile")
@RestController
@RequiredArgsConstructor
public class CommunityProfileApi {

    private final CommunityProfileService communityProfileService;
    private final JwtTokenUtil jwtTokenUtil;

//    접속을 한 유저 프로필 페이지의 유저 정보 불러오는 api
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getUserInfo(
            @PathVariable long id,
            @CookieValue(value = "accessToken", required = false) String accessToken
    ) {
        Long userId = null;
        Map<String,Object> req = new HashMap<>();
        if (accessToken != null) {
            try {
                Claims claims = jwtTokenUtil.parseToken(accessToken);
                userId = Long.parseLong((String) claims.get("id"));
            } catch (Exception e) {
                // 토큰이 유효하지 않으면 비로그인으로 처리
            }
        }
        req.put("id", id);
        req.put("userId", userId);
        CommunityUserResponseDTO communityUserResponseDTO = communityProfileService.getUserInfo(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "커뮤니티 유저 프로필 정보 로드 성공", communityUserResponseDTO));
    }
}
