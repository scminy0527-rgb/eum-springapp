package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.WordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/words")
public class WordsApi {
    private final WordsService wordsService;

    @GetMapping("/edu/{eduId}")
    @Operation(summary = "학습별 단어 목록 조회", description = "학습 번호로 해당 학습에 포함된 단어 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습별 단어 목록 조회 성공")
    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    @Parameter(
            name = "eduId",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getWordsByEduId(@PathVariable Long eduId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습별 단어 목록 조회 성공", wordsService.getWordsByEduId(eduId)));
    }

    @GetMapping("/edu/{eduId}/random")
    @Operation(summary = "학습별 랜덤 단어 목록 조회", description = "특정 학습에 연결된 단어 중 학습-단어 매핑 번호 기준으로 랜덤 단어 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습별 랜덤 단어 목록 조회 성공")
    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    @Parameter(
            name = "eduId",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "limit",
            description = "조회할 랜덤 단어 개수",
            required = false,
            in = ParameterIn.QUERY,
            example = "5",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getRandomWordsByEduId(@PathVariable Long eduId, @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습별 랜덤 단어 목록 조회 성공",  wordsService.getRandomWordsByEduId(eduId, limit))
                );
    }

    @GetMapping("/{id}")
    @Operation(summary = "단어 상세 조회", description = "단어 번호로 단어 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "단어 상세 조회 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "404", description = "단어 정보를 찾을 수 없음")
    @Parameter(
            name = "id",
            description = "단어 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getWordById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "단어 상세 조회 성공", wordsService.getWordById(id)));
    }
}
