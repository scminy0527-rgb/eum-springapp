package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.EduVideoService;
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
@RequestMapping("/api/edu-videos")
public class EduVideoApi {
    private final EduVideoService eduVideoService;

    @GetMapping("/fairy-tales/random")
    @Operation(summary = "동화 영상 랜덤 조회", description = "학습 메인 화면에 표시할 동화 영상을 랜덤으로 1개 조회합니다.")
    @ApiResponse(responseCode = "200", description = "동화 영상 랜덤 조회 성공")
    @ApiResponse(responseCode = "404", description = "등록된 동화 영상 없음")
    public ResponseEntity<ApiResponseDTO> getRandomFairyTaleVideo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "동화 영상 랜덤 조회 성공", eduVideoService.getRandomFairyTaleVideo()));
    }


    @GetMapping("/{id}")
    @Operation(summary = "수어 영상 상세 조회", description = "수어 영상 번호로 영상 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "수어 영상 상세 조회 성공")
    @ApiResponse(responseCode = "404", description = "수어 영상 정보를 찾을 수 없음")
    @Parameter(
            name = "id",
            description = "수어 영상 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getEduVideoById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "수어 영상 상세 조회 성공", eduVideoService.getEduVideoById(id)));

    }
}
