package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.vo.EduVO;
import com.app.springapp.service.EduService;
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
@RequestMapping("/api/admin/edus")
public class AdminEduApi {
    private final EduService eduService;

    @PostMapping("")
    @Operation(summary = "관리자 학습 등록", description = "관리자가 새로운 학습을 등록합니다.")
    @ApiResponse(responseCode = "201", description = "학습 등록 성공")
    public ResponseEntity<ApiResponseDTO> saveEdu(@RequestBody EduVO eduVO) {
        eduService.saveEdu(eduVO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of(true, "학습 등록 성공"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "관리자 학습 수정", description = "관리자가 학습 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "학습 수정 성공")
    @Parameter(
            name = "id",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> updateEdu(@PathVariable Long id, @RequestBody EduVO eduVO) {
        eduVO.setId(id);
        eduService.updateEdu(eduVO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 수정 성공"));
    }

    @PatchMapping("/{id}/delete")
    @Operation(summary = "관리자 학습 삭제 처리", description = "관리자가 학습을 삭제 처리합니다.")
    @ApiResponse(responseCode = "200", description = "학습 삭제 처리 성공")
    @Parameter(
            name = "id",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> deleteEdu(@PathVariable Long id) {
        eduService.deleteEdu(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 삭제 처리 성공"));
    }

    @GetMapping("/deleted")
    @Operation(summary = "관리자 삭제된 학습 목록 조회", description = "삭제 처리된 학습 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "삭제된 학습 목록 조회 성공")
    public ResponseEntity<ApiResponseDTO> getDeletedEduList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "삭제된 학습 목록 조회 성공", eduService.getDeletedEduList()));
    }

    @PatchMapping("/{id}/restore")
    @Operation(summary = "관리자 학습 복구 처리", description = "삭제 처리된 학습을 복구합니다.")
    @ApiResponse(responseCode = "200", description = "학습 복구 처리 성공")
    @Parameter(
            name = "id",
            description = "학습 번호",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> restoreEdu(@PathVariable Long id) {
        eduService.restoreEdu(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 복구 처리 성공"));
    }
}
