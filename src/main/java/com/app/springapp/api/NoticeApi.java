package com.app.springapp.api;

import com.app.springapp.domain.dto.NoticeDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.NoticeService;
import com.app.springapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
@Slf4j
public class NoticeApi {

    private final NoticeService noticeService;
    private final JwtTokenUtil jwtTokenUtil;

    // 공지사항 등록
    @PostMapping
    @Operation(summary = "공지사항 등록")
    public ResponseEntity<ApiResponseDTO> save(
            @RequestBody NoticeDTO noticeDTO,
            @CookieValue(name = "accessToken", required = false) String accessToken) {

        if (accessToken == null) {
            return ResponseEntity.status(401).body(ApiResponseDTO.of(false, "인증 실패"));
        }

        // 토큰에서 userId 직접 추출
        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));
        noticeDTO.setUserId(userId);

        noticeService.save(noticeDTO);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "등록 성공"));
    }

    // 공지사항 목록 조회
    @GetMapping
    @Operation(summary = "공지사항 목록 조회")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String noticeCategory,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int size) {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeCategory(noticeCategory);
        noticeDTO.setOffset(offset);
        noticeDTO.setSize(size);

        List<NoticeDTO> notices = noticeService.findAllNotices(noticeDTO);
        int total = noticeService.countNotices(noticeDTO);
        return ResponseEntity.ok(Map.of("notices", notices, "total", total));
    }

    // 공지사항 상세 조회
    @GetMapping("/{id}")
    @Operation(summary = "공지사항 상세 조회")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        NoticeDTO notice = noticeService.findNoticeById(id);
        return ResponseEntity.ok(notice);
    }

    // 공지사항 수정
    @PutMapping("/{id}")
    @Operation(summary = "공지사항 수정")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable Long id, @RequestBody NoticeDTO noticeDTO) {
        noticeDTO.setId(id);
        noticeService.update(noticeDTO);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "수정 성공"));
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "공지사항 삭제")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return ResponseEntity.ok(ApiResponseDTO.of(true, "삭제 성공"));
    }
}