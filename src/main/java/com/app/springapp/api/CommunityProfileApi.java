package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.CommunityUserResponseDTO;
import com.app.springapp.domain.dto.response.UserResponseDTO;
import com.app.springapp.service.CommunityProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/community-profile")
@RestController
@RequiredArgsConstructor
public class CommunityProfileApi {

    private final CommunityProfileService communityProfileService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getUserInfo(
            @PathVariable long id
    ) {
        CommunityUserResponseDTO communityUserResponseDTO = communityProfileService.getUserInfo(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "커뮤니티 유저 프로필 정보 로드 성공", communityUserResponseDTO));
    }
}
