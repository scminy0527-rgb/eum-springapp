package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tests")
@Slf4j
public class TestApi {

    private final TestService testService;

    @GetMapping
    @Operation(summary = "시험 목록 조회")
    public ResponseEntity<ApiResponseDTO> getTestList() {
        return ResponseEntity.ok(ApiResponseDTO.of(true, "시험 목록 조회 성공", testService.getTestList()));
    }
}
