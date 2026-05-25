package com.app.springapp.api;

import com.app.springapp.domain.dto.request.CommentRequestDTO;
import com.app.springapp.domain.dto.response.ApiResponseDTO;
import com.app.springapp.domain.dto.response.CommentResponseDTO;
import com.app.springapp.service.CommentService;
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

//    게시글 내 댓글 조회 api
    @GetMapping("/{postId}")
    @Operation(description = "게시글 내 댓글 조회")
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
            @PathVariable Long postId
    ){
        List<CommentResponseDTO> comments = commentService.getAllPostComments(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 불러오기 성공", comments));
    }

//    유저가 작성한 댓글 목록 불러오기
    @GetMapping("/users/{userId}")
    @Operation(description = "유저가 작성 한 댓글 조회")
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

//    게시글 내 댓글 작성
    @PostMapping("/{postId}")
    @Operation(description = "게시글 내 댓글 작성")
    @ApiResponse(responseCode = "200", description = "게시글 내 댓글 작성 성공")
    @ApiResponse(responseCode = "400", description = "게시글 내 댓글 작성 실패 (잘못된 요청)")
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
            @RequestBody CommentRequestDTO commentRequestDTO
    ){
        commentService.writePostComment(postId, commentRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 작성 성공"));
    }

    @PostMapping("/{postId}/replies/{commentId}")
    @Operation(description = "게시글 내 대댓글 작성")
    @ApiResponse(responseCode = "200", description = "대댓글 작성 성공")
    @ApiResponse(responseCode = "400", description = "대댓글 작성 실패 (잘못된 요청)")
    @Parameter(
            name = "postId",
            description = "게시글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    @Parameter(name = "commentId",
            description = "부모 댓글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> writePostReply(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO commentRequestDTO
    ) {
        commentService.writePostReply(postId, commentId, commentRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "대댓글 작성 성공"));
    }

    @PutMapping("/{postId}/{commentId}")
    @Operation(description = "게시글 내 댓글 수정")
    @ApiResponse(responseCode = "200", description = "댓글 수정 성공")
    @ApiResponse(responseCode = "400", description = "해당 댓글 수정 권한 없습니다.")
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
            description = "댓글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> updatePostComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO commentRequestDTO
    ) {
        commentService.updateComment(commentId, commentRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 수정 성공"));
    }

    @DeleteMapping("/{commentId}")
    @Operation(description = "댓글 삭제 (대댓글 포함 소프트 삭제)")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공")
    @ApiResponse(responseCode = "400", description = "해당 댓글 삭제 권한 없습니다.")
    @Parameter(
            name = "commentId",
            description = "댓글 아이디",
            example = "1",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "number")
    )
    public ResponseEntity<ApiResponseDTO> deletePostComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.of(true, "댓글 삭제 성공"));
    }
//    댓글 좋아요 하기
    @GetMapping("/likes/{commentId}")
    public ResponseEntity<ApiResponseDTO> addCommentLike(
            @PathVariable Long commentId
    ){
        commentService.addCommentLike(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 좋아요 추가 성공"));
    }

//    댓글 좋아요 취소
    @DeleteMapping("/likes/{commentId}")
    public ResponseEntity<ApiResponseDTO> cancelCommentLike(
            @PathVariable Long commentId
    ){
        commentService.cancelCommentLike(commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDTO.of(true, "댓글 좋아요 취소 성공"));
    }

}
