package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.vo.WordStudyVO;
import com.app.springapp.service.WordStudyService;
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
@RequestMapping("/api/word-studies")
public class WordStudyApi {
    private final WordStudyService wordStudyService;

    @PostMapping("")
    @Operation(summary = "단어 학습 완료 처리", description = "사용자가 단어 학습을 완료하면 학습 기록을 등록하거나 수정합니다.")
    @ApiResponse(responseCode = "200", description = "단어 학습 완료 처리 성공")
    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    public ResponseEntity<ApiResponseDTO> finishWordStudy(@RequestBody WordStudyVO wordStudyVO) {
        wordStudyService.finishWordStudy(wordStudyVO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "단어 학습 완료 처리 성공"));
    }

    @GetMapping("/users/{userId}/edu-word-maps/{eduWordMapId}")
    @Operation(summary = "단어 학습 기록 조회", description = "사용자 번호와 학습-단어 매핑 번호로 단어 학습 기록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "단어 학습 기록 조회 성공")
    @ApiResponse(responseCode = "404", description = "단어 학습 기록을 찾을 수 없음")
    @Parameter(
            name = "userId",
            description = "사용자 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "eduWordMapId",
            description = "학습-단어 매핑 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getWordStudy(@PathVariable Long userId, @PathVariable Long eduWordMapId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "단어 학습 기록 조회 성공", wordStudyService.getWordStudy(userId, eduWordMapId)));
    }

    @GetMapping("/users/{userId}/edus/{eduId}/completed-count")
    @Operation(summary = "학습별 완료 단어 개수 조회", description = "사용자가 특정 학습에서 완료한 단어 개수를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습별 완료 단어 개수 조회 성공")
    @Parameter(
            name = "eduId",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "userId",
            description = "사용자 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getCompletedWordCount(@PathVariable Long eduId, @PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습별 완료 단어 개수 조회 성공", wordStudyService.getCompletedWordCount(userId, eduId)));
    }

    @GetMapping("/edus/{eduId}/total-count")
    @Operation(summary = "학습별 전체 단어 개수 조회", description = "특정 학습에 포함된 전체 단어 개수를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습별 전체 단어 개수 조회 성공")
    @Parameter(
            name = "eduId",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getTotalWordCount(@PathVariable Long eduId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습별 전체 단어 개수 조회 성공", wordStudyService.getTotalWordCount(eduId)));
    }

    @GetMapping("/users/{userId}/today-completed-count")
    @Operation(summary = "오늘 완료한 단어 개수 조회", description = "사용자가 오늘 완료한 단어 개수를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "오늘 완료한 단어 개수 조회 성공")
    @Parameter(
            name = "userId",
            description = "사용자 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getTodayCompletedWordCount(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "오늘 완료한 단어 개수 조회 성공", wordStudyService.getTodayCompletedWordCount(userId)));
    }
}

