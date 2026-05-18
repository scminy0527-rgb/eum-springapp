package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.edu.EduService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/edus")
public class EduApi {
    private final EduService eduService;

    @GetMapping("")
    @Operation(summary = "학습 목록 조회", description = "삭제되지 않은 학습 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습 목록 조회 성공")
    public ResponseEntity<ApiResponseDTO> getEduList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 목록 조회 성공", eduService.getEduList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "학습 상세 조회", description = "학습 번호로 학습 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습 상세 조회 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "404", description = "학습 정보를 찾을 수 없음")
    @Parameter(
            name = "id",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getEduDetailById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 상세 조회 성공", eduService.getEduDetailById(id)));
    }

}
