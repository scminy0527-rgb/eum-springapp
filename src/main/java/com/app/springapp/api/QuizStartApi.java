package com.app.springapp.api;

import com.app.springapp.domain.dto.request.QuizRequestDTO;
import com.app.springapp.domain.dto.request.QuizStartRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.repository.UserDAO;
import com.app.springapp.service.edu.QuizStartService;
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
public class QuizStartApi {
    private final QuizStartService quizStartService;

    @PostMapping("/{quizId}/start")
    @Operation(summary = "퀴즈 시작 기록 등록", description = "사용자가 퀴즈를 시작한 기록을 저장합니다. 이미 시작한 퀴즈면 시작 시간을 갱신합니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈 시작 기록 등록 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "404", description = "퀴즈 또는 유저 정보를 찾을 수 없음")
    @Parameter(
            name = "quizId",
            description = "퀴즈 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> startQuiz(
            @PathVariable Long quizId,
            @RequestBody QuizStartRequestDTO quizStartRequestDTO
    ) {
        quizStartService.startQuiz(quizStartRequestDTO.getUserId(), quizId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈 시작 기록 등록 성공", null));
    }


    @PostMapping("/{quizId}/progress")
    @Operation(summary = "퀴즈 진행 문제 수 증가", description = "사용자가 퀴즈 문제를 하나 풀 때 진행 문제 수를 증가시킵니다.")
    @ApiResponse(responseCode = "200", description = "퀴즈 진행 문제 수 증가 성공")
    @ApiResponse(responseCode = "404", description = "퀴즈 시작 기록을 찾을 수 없음")
    @Parameter(
            name = "quizId",
            description = "퀴즈 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> updateProgress(
            @PathVariable Long quizId,
            @RequestBody QuizStartRequestDTO quizStartRequestDTO
    ) {
        quizStartService.updateProgress(
                quizStartRequestDTO.getUserId(),
                quizId,
                quizStartRequestDTO.getTotalCount(),
                quizStartRequestDTO.getIsCorrect()

        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "퀴즈 진행 문제 수 증가 성공", null));
    }



}
