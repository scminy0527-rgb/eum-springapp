package com.app.springapp.api;

import com.app.springapp.domain.dto.request.QuizSubmitRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.edu.QuizAttemptService;
import com.app.springapp.service.edu.QuizQuestionService;
import com.app.springapp.service.edu.QuizService;
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
@RequestMapping("/api/quizzes")
public class QuizApi {
    private final QuizService quizService;
    private final QuizQuestionService quizQuestionService;
    private final QuizAttemptService quizAttemptService;

    @GetMapping("")
    @Operation(summary = "퀴즈 목록 조회", description = "전체 퀴즈 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈 목록 조회 성공")
    public ResponseEntity<ApiResponseDTO> getQuizList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈 목록 조회 성공", quizService.getQuizList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "퀴즈 상세 조회", description = "퀴즈 번호로 퀴즈 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈 상세 조회 성공")
    @ApiResponse(responseCode = "404", description = "퀴즈 정보를 찾을 수 없음")
    @Parameter(
            name = "id",
            description = "퀴즈 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getQuizById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈 상세 조회 성공", quizService.getQuizById(id)));
    }

    @GetMapping("/{quizId}/questions")
    @Operation(summary = "퀴즈별 문제 목록 조회", description = "퀴즈 번호로 해당 퀴즈에 포함된 문제 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈별 문제 목록 조회 성공")
    @Parameter(
            name = "quizId",
            description = "퀴즈 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getQuestionsByQuizId(@PathVariable Long quizId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈별 문제 목록 조회 성공", quizQuestionService.getQuestionsByQuizId(quizId)));
    }

    @PostMapping("/{quizId}/submit")
    @Operation(summary = "퀴즈 제출 및 채점", description = "사용자의 퀴즈 답안을 제출하고 채점 결과를 저장합니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈 제출 성공")
    @Parameter(
            name = "quizId",
            description = "퀴즈 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> submitQuiz(
            @PathVariable Long quizId,
            @RequestBody QuizSubmitRequestDTO requestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈 제출 성공", quizAttemptService.submitQuiz(quizId, requestDTO)));
    }
}
