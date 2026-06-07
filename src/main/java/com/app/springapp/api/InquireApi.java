package com.app.springapp.api;

import com.app.springapp.domain.dto.InquireDTO;
import com.app.springapp.domain.dto.request.InquireUpdateRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.InquireService;
import com.app.springapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquire")
@Slf4j
public class InquireApi {

    private final InquireService inquireService;
    private final JwtTokenUtil jwtTokenUtil;

    // 문의 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "문의 등록")
    public ResponseEntity<ApiResponseDTO> save(
            @RequestPart("inquireType") String inquireType,
            @RequestPart("inquireTitle") String inquireTitle,
            @RequestPart("inquireContent") String inquireContent,
            @RequestPart("inquireEmail") String inquireEmail,
            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            @CookieValue(name = "accessToken", required = false) String accessToken) throws IOException {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));

        InquireDTO inquireDTO = new InquireDTO();
        inquireDTO.setUserId(userId);
        inquireDTO.setInquireType(inquireType);
        inquireDTO.setInquireTitle(inquireTitle);
        inquireDTO.setInquireContent(inquireContent);
        inquireDTO.setInquireEmail(inquireEmail);

        inquireService.saveWithFile(inquireDTO, files);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "문의 등록 성공"));
    }

    // 내 문의 목록 조회
    @GetMapping
    @Operation(summary = "내 문의 목록 조회")
    public ResponseEntity<?> findAll(
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));

        InquireDTO inquireDTO = new InquireDTO();
        inquireDTO.setUserId(userId);

        List<InquireDTO> list = inquireService.findAllInquires(inquireDTO);
        return ResponseEntity.ok(list);
    }

    // 문의 상세 조회
    @GetMapping("/{id}")
    @Operation(summary = "문의 상세 조회")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        InquireDTO inquire = inquireService.findInquireById(id);
        return ResponseEntity.ok(inquire);
    }

    //    답변 받기전 유저가 직접 문의 내용을 수정 할수 있게 함
    @Operation(summary = "문의 수정 (답변 전 유저가 문의를 수정할 수 있게 함)")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateContent(@PathVariable Long id,
                                           @RequestBody InquireUpdateRequestDTO requestDTO) {
        InquireDTO inquireDTO = new InquireDTO();
        inquireDTO.setId(id);
        inquireDTO.setInquireContent(requestDTO.getInquireContent());

        inquireService.updateContent(inquireDTO);
        return ResponseEntity.ok("문의가 수정되었습니다.");
    }

    // 문의 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "문의 삭제")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable Long id) {
        inquireService.delete(id);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "삭제 성공"));
    }

    // USER_ROLE중 ADMIN인 사람만 문의 답변 등록
    @PutMapping("/{id}/answer")
    @Operation(summary = "문의 답변 등록")
    public ResponseEntity<ApiResponseDTO> answer(
            @PathVariable Long id,
            @RequestBody InquireDTO inquireDTO,
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        String role = (String) claims.get("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body(ApiResponseDTO.of(false, "권한 없음"));
        }

        inquireDTO.setId(id);
        inquireService.update(inquireDTO);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "답변 등록 성공"));
    }

    // USER_ROLE중 ADMIN인 사람만 문의 전체 조회
    @GetMapping("/admin")
    @Operation(summary = "전체 문의 조회 (관리자)")
    public ResponseEntity<?> findAllForAdmin(
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        String role = (String) claims.get("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body(ApiResponseDTO.of(false, "권한 없음"));
        }

        List<InquireDTO> list = inquireService.findAllInquiresForAdmin();
        return ResponseEntity.ok(list);
    }
}