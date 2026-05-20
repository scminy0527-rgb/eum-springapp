package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.vo.EduVideoVO;
import com.app.springapp.service.EduVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/edu-videos")
public class AdminEduVideoApi {
    private final EduVideoService eduVideoService;

    @PostMapping("")
    @Operation(summary = "관리자 수어 영상 등록", description = "관리자가 수어 영상을 등록합니다.")
    @ApiResponse(responseCode = "201", description = "수어 영상 등록 성공")
    public ResponseEntity<ApiResponseDTO> saveEduVideo(@RequestBody EduVideoVO eduVideoVO) {
        eduVideoService.saveEduVideo(eduVideoVO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of(true, "수어 영상 등록 성공"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "관리자 수어 영상 수정", description = "관리자가 수어 영상 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "수어 영상 수정 성공")
    public ResponseEntity<ApiResponseDTO> updateEduVideo(@PathVariable Long id, @RequestBody EduVideoVO eduVideoVO) {
        eduVideoVO.setId(id);
        eduVideoService.updateEduVideo(eduVideoVO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "수어 영상 수정 성공"));
    }
}
