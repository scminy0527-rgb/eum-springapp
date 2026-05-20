package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.edu.SignWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sign-words")
public class SignWordApi {

    private final SignWordService signWordService;

    @GetMapping(value = "", produces = "application/json")
    @Operation(summary = "수어 OpenAPI 검색", description = "문화공공데이터 수어 OpenAPI에서 수어 정보를 검색합니다.")
    @ApiResponse(responseCode = "200", description = "수어 검색 성공")
    public ResponseEntity<ApiResponseDTO> searchSignWords(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int numOfRows
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "수어 검색 성공", signWordService.searchSignWords(keyword, pageNo, numOfRows)));
    }
}
