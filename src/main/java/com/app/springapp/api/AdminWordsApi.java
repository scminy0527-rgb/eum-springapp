package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.vo.WordsVO;
import com.app.springapp.service.WordsService;
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
@RequestMapping("/api/admin/words")
public class AdminWordsApi {
    private final WordsService wordsService;

    @PostMapping("")
    @Operation(summary = "관리자 단어 등록", description = "관리자가 수어 단어를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "단어 등록 성공")
    public ResponseEntity<ApiResponseDTO> saveWord(@RequestBody WordsVO wordsVO) {
        wordsService.saveWord(wordsVO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of(true, "단어 등록 성공"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "관리자 단어 수정", description = "관리자가 수어 단어 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "단어 수정 성공")
    public ResponseEntity<ApiResponseDTO> updateWord(@PathVariable Long id, @RequestBody WordsVO wordsVO) {
        wordsVO.setId(id);
        wordsService.updateWord(wordsVO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "단어 수정 성공"));
    }
}
