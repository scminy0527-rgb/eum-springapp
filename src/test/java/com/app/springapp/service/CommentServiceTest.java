package com.app.springapp.service;

import com.app.springapp.domain.dto.request.CommentRequestDTO;
import com.app.springapp.domain.dto.response.CommentResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

//    게시글에 달린 모든 댓글 조회 서비스 테스트
    @Test
    public void getAllPostCommentsTest(){
        Long postId = 1L;
        List<CommentResponseDTO> comments = commentService.getAllPostComments(postId);
        comments.forEach(comment -> {
            log.info(comment.toString());
        });
    }

//    유저 작성 댓글 불러오기 테스트
    @Test
    public void getUserWrittenCommentsTest(){
        Long userId = 1L;
        Map<String, Object> req = new HashMap<>();
        req.put("page", 1);
        Map<String, Object> result = commentService.getUserWrittenComments(userId, req);
        List<CommentResponseDTO> comments = (List<CommentResponseDTO>) result.get("comments");
        comments.stream()
                .forEach(comment -> {log.info(comment.toString());});
    }

//    게시글에 댓글 달기 테스트
    @Test
    public void writePostCommentTest(){
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setCommentContent("댓글 달기 단위테스트");
        Long postId = 1L;
        commentService.writePostComment(postId, commentRequestDTO);
    }

//    대댓글 달기 테스트
    @Test
    public void writePostReplyTest(){
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setCommentContent("대댓글 테스트 입니다.");
//        Long postId = 1L;
        Long postId = 4L;
//        Long postId = 100L;
        Long commentId = 1L;

        commentService.writePostReply(postId, commentId, commentRequestDTO);
    }

//    댓글 수정 테스트
    @Test
    public void updateComment(){
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setCommentContent("댓글 수정 테스트 입니다.");

//        case 1. 자신이 작성한 댓글을 정상적으로 수정하기 (정상 작동)
//        Long commentId = 25L;
//        commentService.updateComment(commentId, commentRequestDTO);

//        case 1-1. 자신이 작성한 대댓글 수정
        Long postId = 42L;
        commentService.updateComment(postId, commentRequestDTO);

//        case 2. 자신이 작성하지 아니한 댓글을 수정 (정상 작동)
//        Long commentId = 1L;
//        commentService.updateComment(commentId, commentRequestDTO);

//        case 3. 존재하지 않는 댓글을 수정하려고 하기 (정상 작동)
//        Long postId = 100L;
//        commentService.updateComment(postId, commentRequestDTO);
    }

//    댓글 삭제 테스트
    @Test
    public void deleteCommentTest(){
//        자신이 작성한 댓글 삭제 (대댓글 없는 댓글) (정상 작동)
//        Long commentId = 5L;

//        자신이 작성하지 아니한 일반 댓글 삭제 (정상 작동)
//        Long commentId = 11L;

//        자신이 작성한 댓글 중 대댓글 있는거 삭제 (정상 작동)
        Long commentId = 1L;
        commentService.deleteComment(commentId);
    }

//    댓글 좋아요 테스트
    @Test
    public void addCommentLikeTest(){
        Long commentId = 1L;
        commentService.addCommentLike(commentId);
    }

//    댓글 좋아요 취소 테스트 (통과)
    @Test
    public void cancelCommentLikeTest(){
//        기존: 35개

        Long commentId = 19L;
        commentService.cancelCommentLike(commentId);
    }
}
