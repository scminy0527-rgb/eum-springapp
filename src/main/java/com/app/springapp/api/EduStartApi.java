package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.EduStartProgressRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.EduStartCompleteResponseDTO;
import com.app.springapp.service.edu.EduStartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/private/api/edu-starts")
public class EduStartApi {
    private final EduStartService eduStartService;

    @PostMapping("/edus/{eduId}")
    @Operation(summary = "학습 시작 기록 등록", description = "로그인 사용자가 학습을 시작한 기록을 저장합니다.")
    @ApiResponse(responseCode = "200", description = "학습 시작 기록 등록 성공")
    @Parameter(
            name = "eduId",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> startEdu(Authentication authentication, @PathVariable Long eduId) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        eduStartService.startEdu(userDTO.getId(), eduId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 시작 기록 등록 성공", null)
        );
    }


    @PatchMapping("/users/{userId}/edus/{eduId}/complete")
    @Operation(summary = "학습 세션 완료 처리", description = "사용자가 랜덤 학습 문제 세트를 완료했을 때 학습 시작 기록을 완료 상태로 변경합니다.")
    @ApiResponse(responseCode = "200", description = "학습 세션 완료 처리 성공")
    @ApiResponse(responseCode = "404", description = "학습 정보를 찾을 수 없음")
    @Parameter(
            name = "userId",
            description = "유저 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "eduId",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "eduStartTime",
            description = "학습 풀이 시간: 초 단위",
            required = true,
            in = ParameterIn.QUERY,
            example = "120",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> completeEduStart(
            @PathVariable Long userId,
            @PathVariable Long eduId,
            @RequestParam int eduStartTime
    ) {
        EduStartCompleteResponseDTO result =
                eduStartService.completeEduStart(userId, eduId, eduStartTime);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 세션 완료 처리 성공", result));
    }


    @GetMapping("/users/{userId}/edus/{eduId}/completed")
    @Operation(summary = "학습 세션 완료 여부 조회", description = "사용자가 해당 학습의 랜덤 문제 세트를 완료했는지 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습 세션 완료 여부 조회 성공")
    @ApiResponse(responseCode = "404", description = "학습 세션 완료 정보를 찾을 수 없음")
    @Parameter(
            name = "userId",
            description = "유저 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "eduId",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> isEduStartCompleted(@PathVariable Long userId, @PathVariable Long eduId) {
        boolean completed = eduStartService.isEduStartCompleted(userId, eduId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 세션 완료 여부 조회 성공", completed));
    }

    @PostMapping("/users/{userId}/edus/{eduId}/progress")
    @Operation(summary = "학습 세션 문제 풀이 결과 반영", description = "진행 중인 학습 세션에 푼 문제 수와 정답 수를 반영합니다.")
    @ApiResponse(responseCode = "200", description = "학습 세션 문제 풀이 결과 반영 성공")
    @ApiResponse(responseCode = "404", description = "진행 중인 학습 세션을 찾을 수 없음")
    @Parameter(
            name = "userId",
            description = "유저 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "eduId",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> recordProgress(
            @PathVariable Long userId,
            @PathVariable Long eduId,
            @RequestBody EduStartProgressRequestDTO requestDTO
    ) {
        eduStartService.recordProgress(userId, eduId, requestDTO.getQuestionNumber(), requestDTO.getIsCorrect());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 세션 문제 풀이 결과 반영 성공", null));
    }


    @PostMapping("/users/{userId}/edus/{eduId}/roadmap-reward")
    @Operation(summary = "학습 로드맵 이벤트 보상 수령", description = "완료한 학습의 로드맵 이벤트 보상을 수령합니다.")
    @ApiResponse(responseCode = "200", description = "학습 로드맵 이벤트 보상 수령 성공")
    @ApiResponse(responseCode = "400", description = "학습을 완료하지 않은 사용자")
    @ApiResponse(responseCode = "404", description = "학습 시작 기록을 찾을 수 없음")
    public ResponseEntity<ApiResponseDTO> claimRoadmapReward(@PathVariable Long userId, @PathVariable Long eduId) {
        int rewardExp = eduStartService.claimRoadmapReward(userId, eduId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 로드맵 이벤트 보상 수령 성공", rewardExp));
    }

}
