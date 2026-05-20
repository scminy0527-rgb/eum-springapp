package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.edu.QuizAttemptService;
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
@RequestMapping("/api/quiz-attempts")
public class QuizAttemptApi {
    private final QuizAttemptService quizAttemptService;

    @GetMapping("/{id}")
    @Operation(summary = "퀴즈 응시 결과 조회", description = "퀴즈 응시 번호로 응시 결과를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈 응시 결과 조회 성공")
    @ApiResponse(responseCode = "404", description = "퀴즈 응시 결과를 찾을 수 없음")
    @Parameter(
            name = "id",
            description = "퀴즈 응시 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getAttemptById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈 응시 결과 조회 성공", quizAttemptService.getAttemptById(id)));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "사용자별 퀴즈 응시 기록 조회", description = "사용자 번호로 퀴즈 응시 기록 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "사용자별 퀴즈 응시 기록 조회 성공")
    @Parameter(
            name = "userId",
            description = "사용자 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getAttemptsByUserId(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "사용자별 퀴즈 응시 기록 조회 성공", quizAttemptService.getAttemptsByUserId(userId)));
    }

    @GetMapping("/{attemptId}/details")
    @Operation(summary = "퀴즈 응시별 문제 결과 목록 조회", description = "퀴즈 응시 번호로 문제별 제출 결과 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈 응시별 문제 결과 목록 조회 성공")
    @Parameter(
            name = "attemptId",
            description = "퀴즈 응시 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getAttemptDetailsByAttemptId(@PathVariable Long attemptId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈 응시별 문제 결과 목록 조회 성공", quizAttemptService.getAttemptDetailsByAttemptId(attemptId)));
    }
}
