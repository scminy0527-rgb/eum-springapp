package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/posts")
public class PrivatePostApi {

    private final PostService postService;

    @PostMapping("")
    @Operation(summary = "게시글 작성", description = "게시글 작성하기 (로그인 필요)")
    @ApiResponse(responseCode = "201", description = "게시글 작성 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ApiResponseDTO> writePost(
            @RequestBody PostRequestDTO postRequestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        postService.writePost(userId, postRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of(true, "게시글 작성 성공"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정", description = "게시글 수정하기 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "게시글 수정 성공")
    @ApiResponse(responseCode = "400", description = "해당 게시글 수정 권한 없습니다.")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "id",
            description = "게시글 번호",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequestDTO postRequestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        postService.updatePost(userId, id, postRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "게시글 수정 성공"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제", description = "게시글 소프트 삭제 (로그인 필요)")
    @ApiResponse(responseCode = "204", description = "게시글 삭제 성공")
    @ApiResponse(responseCode = "400", description = "해당 게시글 삭제 권한 없습니다.")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "id",
            description = "게시글 번호",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> deletePost(
            @PathVariable Long id,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        postService.deletePost(userId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.of(true, "게시글 삭제 성공"));
    }

    @GetMapping("/like/{postId}")
    @Operation(summary = "게시글 좋아요", description = "게시글 좋아요 하기 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "게시글 좋아요 성공")
    @ApiResponse(responseCode = "400", description = "해당 게시글에 좋아요 할 수 없습니다")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "postId",
            description = "게시글 번호",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> increasePostLikeCount(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        postService.increasePostLikeCount(userId, postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "좋아요 성공"));
    }

    @DeleteMapping("/like/{postId}")
    @Operation(summary = "게시글 좋아요 취소", description = "게시글 좋아요 취소 하기 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "게시글 좋아요 취소 성공")
    @ApiResponse(responseCode = "400", description = "해당 게시글 좋아요 취소 불가능")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "postId",
            description = "게시글 번호",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> cancelPostLike(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        postService.cancelPostLike(userId, postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "좋아요 취소 성공"));
    }
}
