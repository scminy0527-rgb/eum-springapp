package com.app.springapp.api;

import com.app.springapp.domain.dto.request.EduWordFromSignWordRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.vo.EduWordMapVO;
import com.app.springapp.service.edu.EduWordMapService;
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
@RequestMapping("/api/edu-word-maps")
public class EduWordMapApi {
    private final EduWordMapService eduWordMapService;

    @GetMapping("")
    @Operation(summary = "학습 단어 매핑 목록 조회", description = "전체 학습 단어 매핑 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습 단어 매핑 목록 조회 성공")
    public ResponseEntity<ApiResponseDTO> getEduWordMaps() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 단어 매핑 목록 조회 성공",eduWordMapService.getEduWordMaps()));
    };


    @GetMapping("/edus/{eduId}")
    @Operation(summary = "학습별 단어 매핑 조회", description = "특정 학습에 연결된 단어 매핑 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습별 단어 매핑 조회 성공")
    public ResponseEntity<ApiResponseDTO> getEduWordMapsByEduId(@PathVariable Long eduId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습별 단어 매핑 조회 성공", eduWordMapService.getEduWordMapsByEduId(eduId))
        );
    }


    @GetMapping("/words/{wordsId}")
    @Operation(summary = "단어별 학습 매핑 조회", description = "특정 단어가 연결된 학습 매핑 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "단어별 학습 매핑 조회 성공")
    public ResponseEntity<ApiResponseDTO> getEduWordMapsByWordsId(@PathVariable Long wordsId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "단어별 학습 매핑 조회 성공", eduWordMapService.getEduWordMapsByWordsId(wordsId))
        );
    }


    @GetMapping("/edus/{eduId}/count")
    @Operation(summary = "학습별 단어 매핑 개수 조회", description = "특정 학습에 연결된 단어 개수를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "학습별 단어 매핑 개수 조회 성공")
    public ResponseEntity<ApiResponseDTO> getEduWordMapCountByEduId(@PathVariable Long eduId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습별 단어 매핑 개수 조회 성공", eduWordMapService.getEduWordMapCountByEduId(eduId))
        );
    }


    @PostMapping
    @Operation(summary = "학습 단어 매핑 등록", description = "학습과 단어를 연결합니다.")
    @ApiResponse(responseCode = "200", description = "학습 단어 매핑 등록 성공")
    public ResponseEntity<ApiResponseDTO> saveEduWordMap(@RequestBody EduWordMapVO eduWordMapVO) {
        eduWordMapService.saveEduWordMap(eduWordMapVO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 단어 매핑 등록 성공")
        );
    }


    // OpenAPI 수어 데이터를 학습 단어로 등록
    @PostMapping("/from-sign-word")
    @Operation(summary = "OpenAPI 수어 학습 단어 등록", description = "TBL_SIGN_WORD에 저장된 OpenAPI 수어 데이터를 학습 단어로 등록하고 학습에 연결합니다.")
    @ApiResponse(responseCode = "200", description = "OpenAPI 수어 학습 단어 등록 성공")
    public ResponseEntity<ApiResponseDTO> saveEduWordFromSignWord(@RequestBody EduWordFromSignWordRequestDTO requestDTO) {
        eduWordMapService.saveEduWordFromSignWord(requestDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "OpenAPI 수어 학습 단어 등록 성공"));
    };


    @PutMapping("/{id}")
    @Operation(summary = "학습 단어 매핑 수정", description = "학습 단어 매핑 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "학습 단어 매핑 수정 성공")
    public ResponseEntity<ApiResponseDTO> updateEduWordMap(@PathVariable Long id, @RequestBody EduWordMapVO eduWordMapVO) {
        eduWordMapVO.setId(id);
        eduWordMapService.updateEduWordMap(eduWordMapVO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 단어 매핑 수정 성공")
        );
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "학습 단어 매핑 삭제", description = "ID 기준으로 학습 단어 매핑을 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "학습 단어 매핑 삭제 성공")
    public ResponseEntity<ApiResponseDTO> deleteEduWordMap(@PathVariable Long id) {
        eduWordMapService.deleteEduWordMap(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "학습 단어 매핑 삭제 성공")
        );
    }

}

