package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/private/api/files")
@RequiredArgsConstructor
public class FileApi {

    private final FileService fileService;

    // 파일 1개 업로드
    @PostMapping("/single")
    @Operation(summary = "파일 1개 업로드", description = "파일 1개를 AWS S3에 업로드합니다.")
    @ApiResponse(responseCode = "200", description = "파일 업로드 성공")
    @ApiResponse(responseCode = "400", description = "파일 업로드 실패 (잘못된 파일 또는 빈 파일)")
    public ResponseEntity<ApiResponseDTO> upload(
            @RequestParam("uploadFile") MultipartFile uploadFile
    ) {
        return ResponseEntity.ok(fileService.uploadFile(uploadFile));
    }

    // 파일 여러 개 업로드
    @PostMapping
    @Operation(summary = "파일 여러 개 업로드", description = "파일 여러 개를 AWS S3에 업로드합니다.")
    @ApiResponse(responseCode = "200", description = "파일 업로드 성공")
    @ApiResponse(responseCode = "400", description = "파일 업로드 실패 (잘못된 파일 또는 빈 파일 포함)")
    public ResponseEntity<ApiResponseDTO> uploads(
            @RequestParam("uploadFiles") List<MultipartFile> uploadFiles
    ) {
        return ResponseEntity.ok(fileService.uploadFiles(uploadFiles));
    }

    // 업로드 파일 조회
    @GetMapping("/{fileName}")
    @Operation(summary = "파일 조회", description = "S3에 저장된 파일을 바이트 배열로 반환합니다.")
    @ApiResponse(responseCode = "200", description = "파일 조회 성공")
    @ApiResponse(responseCode = "400", description = "파일 조회 실패 (존재하지 않는 파일)")
    @Parameter(name = "fileName", description = "조회할 파일명 (S3 경로)", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string"))
    public ResponseEntity<byte[]> display(
            @PathVariable String fileName
    ) {

        ResponseBytes<GetObjectResponse> responseBytes =
                fileService.getDisplayPath(fileName);

        String contentType =
                responseBytes.response().contentType();

        if (contentType == null || contentType.isBlank()) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(responseBytes.asByteArray());
    }
}