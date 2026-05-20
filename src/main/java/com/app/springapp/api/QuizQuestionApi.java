package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.edu.QuizChoiceService;
import com.app.springapp.service.edu.QuizQuestionService;
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
@RequestMapping("/api/quiz-questions")
public class QuizQuestionApi {
    private final QuizQuestionService quizQuestionService;
    private final QuizChoiceService quizChoiceService;

    @GetMapping("/{id}")
    @Operation(summary = "퀴즈 문제 상세 조회", description = "퀴즈 문제 번호로 문제 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈 문제 상세 조회 성공")
    @ApiResponse(responseCode = "404", description = "퀴즈 문제 정보를 찾을 수 없음")
    @Parameter(
            name = "id",
            description = "퀴즈 문제 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getQuestionById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈 문제 상세 조회 성공", quizQuestionService.getQuestionById(id)));
    }

    @GetMapping("/{questionId}/choices")
    @Operation(summary = "문제별 보기 목록 조회", description = "퀴즈 문제 번호로 해당 문제의 보기 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "문제별 보기 목록 조회 성공")
    @Parameter(
            name = "questionId",
            description = "퀴즈 문제 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getChoicesByQuestionId(@PathVariable Long questionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "문제별 보기 목록 조회 성공", quizChoiceService.getChoicesByQuestionId(questionId)));
    }
}
