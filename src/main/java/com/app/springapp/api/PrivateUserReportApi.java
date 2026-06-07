package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.UserReportRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/user-reports")
@RequiredArgsConstructor
public class PrivateUserReportApi {
    private final UserReportService userReportService;

//    유저 신고
    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> post(
            @RequestBody UserReportRequestDTO userReportRequestDTO,
            Authentication authentication
    ) {
        Long userId = ((UserDTO) authentication.getPrincipal()).getId();
        userReportService.reportUser(userId, userReportRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "유저 신고 성공"));
    }
}
