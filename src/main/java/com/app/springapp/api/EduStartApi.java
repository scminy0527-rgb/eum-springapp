package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
