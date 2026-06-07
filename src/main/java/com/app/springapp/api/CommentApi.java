package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.CommentResponseDTO;
import com.app.springapp.service.CommentService;
import com.app.springapp.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentApi {

    private final CommentService commentService;
    private final JwtTokenUtil jwtTokenUtil;

//    게시글 내 댓글 조회 api
    @GetMapping("/{postId}")
    @Operation(summary = "댓글 조회", description = "게시글 내 댓글 조회")
    @ApiResponse(responseCode = "200", description = "게시글 내 댓글 조회 성공")
    @ApiResponse(responseCode = "400", description = "게시글 내 댓글 조회 실패 (잘못된 요청)")
    @Parameter(
            name = "postId",
            description = "게시글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getAllPostComments(
            @PathVariable Long postId,
            @CookieValue(value = "accessToken", required = false) String accessToken
    ){
        Long userId = null;
        if (accessToken != null) {
            try {
                Claims claims = jwtTokenUtil.parseToken(accessToken);
                userId = Long.parseLong((String) claims.get("id"));
            } catch (Exception e) {
                // 토큰이 유효하지 않으면 비로그인으로 처리
            }
        }
        List<CommentResponseDTO> comments = commentService.getAllPostComments(postId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 불러오기 성공", comments));
    }

//    유저가 작성한 댓글 목록 불러오기
    @GetMapping("/users/{userId}")
    @Operation(summary = "유저 작성 댓글 조회", description = "유저가 작성 한 댓글 조회")
    @ApiResponse(responseCode = "200", description = "유저 작성 댓글 조회 성공")
    @ApiResponse(responseCode = "400", description = "유저 작성 댓글 조회 실패 (잘못된 요청)")
    @Parameter(
            name = "userId",
            description = "유저 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "page",
            description = "페이지 번호",
            example = "1",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "order",
            description = "댓글 정렬 기준",
            example = "latest",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    @Parameter(
            name = "keyword",
            description = "댓글 부분 검색 키워드",
            example = "수어",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    public ResponseEntity<ApiResponseDTO> getUserWrittenComments(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "latest") String order,
            @RequestParam(defaultValue = "") String keyword
    ){
        Map<String, Object> req = new HashMap<>();
        req.put("page", page);
        req.put("order", order);
        req.put("keyword", keyword);

        Map<String, Object> result = commentService.getUserWrittenComments(userId, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "유저 작성 댓글 불러오기 성공", result));
    }

}
