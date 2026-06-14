package com.app.springapp.api;

import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.PostService;
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
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostApi {
    private final PostService postService;
    private final JwtTokenUtil jwtTokenUtil;


//    전체 포스트 가져오는거 정의 (페이지네이션 적용)
    @GetMapping("")
    @Operation(summary = "포스트 로드", description = "게시글 전체 로드")
    @ApiResponse(responseCode = "200", description = "게시글 목록 로드 성공")
    @ApiResponse(responseCode = "400", description = "게시글 목록 로드 실패 (잘못된 요청)")
    @Parameter(
            name = "postTag",
            description = "게시글 타입 태그",
            example = "자유게시판",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "String")
    )
    @Parameter(
            name = "keyword",
            description = "게시글 검색 키워드",
            example = "수어 공부",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "String")
    )
    public ResponseEntity<ApiResponseDTO> getAllPosts(
            @RequestParam(required = false, defaultValue = "") String postTag,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "latest") String order
    ){
        Map<String,Object> map = new HashMap<>();
        map.put("postTag",postTag);
        map.put("page",page);
        map.put("keyword",keyword);
        map.put("order",order);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "게시글 목록 불러오기 성공", postService.getAllPosts(map)));
    }

//    특정 게시글 가져오기
    @GetMapping("/{id}")
    @Operation(summary = "특정 포스트 로드", description = "특정 게시글 상세정보 불러오기")
    @ApiResponse(responseCode = "200", description = "게시글 정보 로드 성공")
    @ApiResponse(responseCode = "400", description = "게시글 정보 로드 실패 (잘못된 요청)")
    @Parameter(
            name = "id",
            description = "게시글 번호",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> getPostById(
            @PathVariable Long id,
            @CookieValue(value = "accessToken", required = false) String accessToken
    ) {
        Long userId = null;
        if (accessToken != null) {
            try {
                Claims claims = jwtTokenUtil.parseToken(accessToken);
                userId = Long.parseLong((String) claims.get("id"));
            } catch (Exception e) {
                // 토큰이 유효하지 않으면 비로그인으로 처리
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "포스트 로드 성공", postService.getPost(id, userId)));
    }

//    유저 프로필에서 게시글 가져오기
    @GetMapping("/user/{userId}")
    @Operation(summary = "유저 작성 게시글 조회", description = "유저가 작성한 게시글 목록 불러오기")
    @ApiResponse(responseCode = "200", description = "유저 작성 게시글 목록 로드 성공")
    @ApiResponse(responseCode = "400", description = "유저 작성 게시글 목록 로드 실패 (잘못된 요청)")
    @Parameter(
            name = "userId",
            description = "유저 번호",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "page",
            description = "게시글 페이지 번호",
            example = "1",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "order",
            description = "게시글 정렬 기준",
            example = "latest",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    @Parameter(
            name = "keyword",
            description = "게시글 부분 검색 키워드",
            example = "수어",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    public ResponseEntity<ApiResponseDTO> getPostByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "latest") String order,
            @RequestParam(defaultValue = "") String keyword
    ){
//        유저 아이디는 추후 실제로 불러와질 수 있을 때 정하기
//        Long userId = 1L;
        Map<String,Object> req = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        req.put("page", page);
        req.put("order", order);
        req.put("keyword", keyword);

        result = postService.getUserPosts(userId, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "유저 게시글 로드 성공", result));
    }

//    유저 프로필에서 유저가 좋아요 한 게시글 가져오기
    @GetMapping("/user/{userId}/likes")
    @Operation(summary = "유저가 좋아요 한 게시글 목록", description = "유저가 좋아요 한 게시글 목록 불러오기")
    @ApiResponse(responseCode = "200", description = "유저 좋아요 게시글 목록 로드 성공")
    @ApiResponse(responseCode = "400", description = "유저 좋아요 게시글 목록 로드 실패 (잘못된 요청)")
    @Parameter(
            name = "userId",
            description = "유저 번호",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "page",
            description = "게시글 페이지 번호",
            example = "1",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "order",
            description = "게시글 정렬 기준",
            example = "latest",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    @Parameter(
            name = "keyword",
            description = "게시글 부분 검색 키워드",
            example = "수어",
            required = false,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    public ResponseEntity<ApiResponseDTO> getPostLikedByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "latest") String order,
            @RequestParam(defaultValue = "") String keyword
    ){
        Map<String, Object> req = new HashMap<>();
        req.put("page", page);
        req.put("order", order);
        req.put("keyword", keyword);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "유저 좋아요 게시글 로드 성공", postService.getUserLikedPosts(userId, req)));
    }

}
