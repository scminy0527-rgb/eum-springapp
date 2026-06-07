package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.CommentReportRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.CommentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/comment-reports")
@RequiredArgsConstructor
public class PrivateCommentReportApi {
    private final CommentReportService commentReportService;

    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> post(
            @RequestBody CommentReportRequestDTO commentReportRequestDTO,
            Authentication authentication
    ) {
        Long userId = ((UserDTO) authentication.getPrincipal()).getId();
        commentReportService.reportComment(userId, commentReportRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 신고 성공"));
    }
}
