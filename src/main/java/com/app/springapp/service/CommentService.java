package com.app.springapp.service;

import com.app.springapp.domain.dto.request.CommentRequestDTO;
import com.app.springapp.domain.dto.response.CommentResponseDTO;

import java.util.List;
import java.util.Map;

public interface CommentService {
    public List<CommentResponseDTO> getAllPostComments(Long postId);

//    유저가 남긴 댓글 조회 (페이지네이션)
    public Map<String, Object> getUserWrittenComments(Long userId, Map<String, Object> req);

    public void writePostComment(Long postId, CommentRequestDTO commentRequestDTO);

    public void writePostReply(Long postId, Long commentId, CommentRequestDTO commentRequestDTO);

    public void updateComment(Long commentId, CommentRequestDTO commentRequestDTO);

    public void deleteComment(Long commentId);

//    댓글 좋아요 남기기
    public void addCommentLike(Long commentId);

//    댓글 좋아요 취소
    public void cancelCommentLike(Long commentId);
}
