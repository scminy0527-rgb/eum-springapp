package com.app.springapp.repository;

import com.app.springapp.domain.dto.CommentDTO;
import com.app.springapp.domain.vo.CommentVO;
import com.app.springapp.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommentDAO {
    private final CommentMapper commentMapper;

//    게시글에 작성 된 댓글 조회
    public List<CommentDTO> findAllByPostId(Long postId){
        return commentMapper.selectAllByPostId(postId);
    }

//    유저가 작성 한 댓글 조회 (페이지네이션)
    public List<CommentDTO> findAllByUserId(Map<String, Object> filters){
        return commentMapper.selectAllByUserId(filters);
    }

//    유저가 작성 한 댓글 총 개수
    public int countByUserId(Long userId){
        return commentMapper.countByUserId(userId);
    }

//    댓글 작성
    public void save(CommentVO commentVO){
        commentMapper.insert(commentVO);
    }

    public int existByIdAndPostId(CommentVO commentVO) {
        return commentMapper.existByIdAndPostId(commentVO);
    }

    public int existByIdAndUserId(CommentVO commentVO) {
        return commentMapper.existByIdAndUserId(commentVO);
    }

    public void update(CommentVO commentVO) {
        commentMapper.update(commentVO);
    }

    public void updateIsDeleted(CommentVO commentVO) {
        commentMapper.updateIsDeleted(commentVO);
    }

    public void updateRepliesIsDeleted(Long commentId) {
        commentMapper.updateRepliesIsDeleted(commentId);
    }

//    게시글에 달린 모든 댓글 삭제
    public void updateDeleteByPostId(Long postId) {
        commentMapper.updateDeleteByPostId(postId);
    }

    public int isParentComment(Long commentId) {
        return commentMapper.isParentComment(commentId);
    }
}
