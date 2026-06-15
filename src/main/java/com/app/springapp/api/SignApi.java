package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sign")
public class SignApi {

    private final SignService signService;

    @Operation(summary = "수어 번역", description = "텍스트를 수어 keypoint로 변환")
    @GetMapping("/translate")
    public ResponseEntity<ApiResponseDTO> translate(@RequestParam String text) {
        Map<String, Object> result = signService.translate(text);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "수어 번역 성공", result));
    }
}