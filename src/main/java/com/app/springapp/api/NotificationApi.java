package com.app.springapp.api;

import com.app.springapp.domain.dto.NotificationDTO;
import com.app.springapp.service.NotificationService;
import com.app.springapp.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationApi {

    private final NotificationService notificationService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    @Operation(summary = "알림 목록 조회")
    public ResponseEntity<List<NotificationDTO>> getNotifications(
            @CookieValue(name = "accessToken", required = false) String accessToken) {
        if (accessToken == null) return ResponseEntity.status(401).build();
        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));
        return ResponseEntity.ok(notificationService.getNotifications(userId));
    }

    @GetMapping(value = "/unread", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "안읽은 알림 수 조회")
    public ResponseEntity<Integer> countUnread(
            @CookieValue(name = "accessToken", required = false) String accessToken) {
        if (accessToken == null) return ResponseEntity.status(401).build();
        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));
        return ResponseEntity.ok(notificationService.countUnread(userId));
    }

    @PatchMapping("/{id}/read")
    @Operation(summary = "알림 읽음 처리")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("읽음 처리 완료");
    }

    @PatchMapping("/read-all")
    @Operation(summary = "전체 알림 읽음 처리")
    public ResponseEntity<?> markAllAsRead(
            @CookieValue(name = "accessToken", required = false) String accessToken) {
        if (accessToken == null) return ResponseEntity.status(401).build();
        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok("전체 읽음 처리 완료");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "알림 삭제")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }

    // 테스트용
    @PostMapping("/test")
    @Operation(summary = "알림 테스트")
    public ResponseEntity<?> test(
            @CookieValue(name = "accessToken", required = false) String accessToken) {
        if (accessToken == null) return ResponseEntity.status(401).build();
        Map<String, Object> claims = jwtTokenUtil.parseToken(accessToken);
        Long userId = Long.parseLong((String) claims.get("id"));
        notificationService.send(
                userId,
                "INQUIRY_ANSWER",
                "문의 답변 도착",
                "회원님의 문의에 답변이 등록되었습니다.",
                "/customservice/result"
        );
        return ResponseEntity.ok("테스트 알림 전송 완료");
    }
}