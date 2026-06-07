package com.app.springapp.api;

import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.request.CommentRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.service.CommentService;
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
@RequestMapping("/private/comments")
public class PrivateCommentApi {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    @Operation(summary = "댓글 작성", description = "게시글 내 댓글 작성 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "게시글 내 댓글 작성 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "postId",
            description = "게시글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> writePostComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDTO commentRequestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        commentService.writePostComment(postId, userId, commentRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 작성 성공"));
    }

    @PostMapping("/{postId}/replies/{commentId}")
    @Operation(summary = "대댓글 작성", description = "게시글 내 대댓글 작성 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "대댓글 작성 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "postId",
            description = "게시글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    @Parameter(
            name = "commentId",
            description = "부모 댓글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> writePostReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO commentRequestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        commentService.writePostReply(postId, commentId, userId, commentRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "대댓글 작성 성공"));
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "게시글 내 댓글 수정 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "댓글 수정 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "commentId",
            description = "댓글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> updatePostComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO commentRequestDTO,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        commentService.updateComment(commentId, userId, commentRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 수정 성공"));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제 (대댓글 포함 소프트 삭제) (로그인 필요)")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    @Parameter(
            name = "commentId",
            description = "댓글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> deletePostComment(
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        commentService.deleteComment(commentId, userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.of(true, "댓글 삭제 성공"));
    }

    @GetMapping("/likes/{commentId}")
    @Operation(summary = "댓글 좋아요", description = "댓글 좋아요 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "댓글 좋아요 추가 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ApiResponseDTO> addCommentLike(
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        commentService.addCommentLike(commentId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 좋아요 추가 성공"));
    }

    @DeleteMapping("/likes/{commentId}")
    @Operation(summary = "댓글 좋아요 취소", description = "댓글 좋아요 취소 (로그인 필요)")
    @ApiResponse(responseCode = "200", description = "댓글 좋아요 취소 성공")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ApiResponseDTO> cancelCommentLike(
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        Long userId = userDTO.getId();

        commentService.cancelCommentLike(commentId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 좋아요 취소 성공"));
    }
}
