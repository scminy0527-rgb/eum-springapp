package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.PostReportRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.PostReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/post-reports")
@RequiredArgsConstructor
public class PrivatePostReportApi {
    private final PostReportService postReportService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> post(
            @RequestBody PostReportRequestDTO postReportRequestDTO,
            Authentication authentication
    ) {
        Long userId = ((UserDTO) authentication.getPrincipal()).getId();
        postReportService.reportPost(userId, postReportRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "게시글 신고 성공"));
    }
}
