package com.app.springapp.api;

import com.app.springapp.domain.dto.request.PostRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.PostService;
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

//    전체 포스트 가져오는거 정의
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
    public ResponseEntity<ApiResponseDTO> getAllPosts(
            @RequestParam(required = false, defaultValue = "") String postTag,
            @RequestParam(defaultValue = "1") int page
    ){
        Map<String,Object> map = new HashMap<>();
        map.put("postTag",postTag);
        map.put("page",page);
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
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "포스트 로드 성공", postService.getPost(id)));
    }

//    유저 프로필에서 게시글 가져오기
    @GetMapping("/user/{userId}")
    @Operation(description = "유저가 작성한 게시글 목록 불러오기")
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
    public ResponseEntity<ApiResponseDTO> getPostByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page
    ){
//        유저 아이디는 추후 실제로 불러와질 수 있을 때 정하기
//        Long userId = 1L;
        Map<String,Object> req = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        req.put("page", page);

        result = postService.getUserPosts(userId, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "유저 게시글 로드 성공", result));
    }

//    게시글 작성
    @PostMapping("")
    @Operation(description = "게시글 작성하기")
    @ApiResponse(responseCode = "201", description = "게시글 작성 성공")
    @ApiResponse(responseCode = "400", description = "게시글 작성 실패 (잘못된 요청)")
    public ResponseEntity<ApiResponseDTO> writePost(
            @RequestBody PostRequestDTO postRequestDTO
    ){
        postService.writePost(postRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of(true, "게시글 작성 성공"));
    }

//    게시글 수정
    @PutMapping("/{id}")
    @Operation(description = "게시글 수정하기")
    @ApiResponse(responseCode = "200", description = "게시글 수정 성공")
    @ApiResponse(responseCode = "400", description = "해당 게시글 수정 권한 없습니다.")
    public ResponseEntity<ApiResponseDTO> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDTO postRequestDTO
    ){
        postService.updatePost(id, postRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "게시글 수정 성공"));
    }

//    게시글 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제", description = "게시글 소프트 삭제")
    @ApiResponse(responseCode = "204", description = "게시글 삭제 성공")
    @ApiResponse(responseCode = "400", description = "해당 게시글 삭제 권한 없습니다.")
    @Parameter(
            name = "id",
            description = "게시글 번호",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> deletePost(
            @PathVariable Long id
    ){
        postService.deletePost(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.of(true, "게시글 삭제 성공"));
    }
}
