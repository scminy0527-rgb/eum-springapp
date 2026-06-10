package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/follow")
public class PrivateFollowApi {
    private final FollowService followService;

//    유저 팔로우 하기
    @GetMapping("/{followingId}")
    public ResponseEntity<ApiResponseDTO> followUser(
            @PathVariable Long followingId,
            Authentication authentication
    ){
        Long userId = ((UserDTO)  authentication.getPrincipal()).getId();
        followService.userFollow(userId,followingId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "유저 팔로우 성공"));
    }

//    유저 팔로우 취소하기
    @GetMapping("/cancel/{followingId}")
    public ResponseEntity<ApiResponseDTO> cancelFollow(
            @PathVariable Long followingId,
            Authentication authentication
    ){
        Long userId = ((UserDTO)  authentication.getPrincipal()).getId();
        return null;

    }
}
