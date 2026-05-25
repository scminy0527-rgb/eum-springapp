package com.app.springapp.service;

import com.app.springapp.domain.dto.request.CommentRequestDTO;
import com.app.springapp.domain.dto.response.CommentResponseDTO;
import com.app.springapp.domain.vo.CommentLikeVO;
import com.app.springapp.domain.vo.CommentVO;
import com.app.springapp.exception.CommentException;
import com.app.springapp.repository.CommentDAO;
import com.app.springapp.repository.CommentLikeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;
    private final CommunityAuthService communityAuthService;
    private final CommentLikeDAO commentLikeDAO;

    @Override
    public List<CommentResponseDTO> getAllPostComments(Long postId) {
        return commentDAO.findAllByPostId(postId)
                .stream()
                .map(CommentResponseDTO::from)
                .collect(Collectors.toList());
    }

//    유저가 작성 한 댓글 목록 불러오기 (페이지네이션)
    @Override
    public Map<String, Object> getUserWrittenComments(Long userId, Map<String, Object> req) {
        int size = 10;
        int page = (Integer) req.get("page");
        String order = (String) req.get("order");
        String keyword = (String) req.get("keyword");

        int offset = (page - 1) * size;

        Map<String, Object> filters = new HashMap<>();
        filters.put("userId", userId);
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("order", order);
        filters.put("keyword", keyword);

        List<CommentResponseDTO> comments = commentDAO.findAllByUserId(filters)
                .stream()
                .map(CommentResponseDTO::from)
                .collect(Collectors.toList());

        int totalCount = commentDAO.countByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("comments", comments);
        result.put("currentPage", page);
        result.put("totalPages", (int) Math.ceil((double) totalCount / size));
        result.put("size", size);
        result.put("totalCount", totalCount);

        return result;
    }

    @Override
    public void writePostComment(Long postId, CommentRequestDTO commentRequestDTO) {
        Long userId = communityAuthService.getUserId();
        communityAuthService.checkUserValidity(userId);

        CommentVO commentVO = CommentVO.from(commentRequestDTO);
        commentVO.setPostId(postId);
        commentVO.setUserId(userId);

        try {
            commentDAO.save(commentVO);
        } catch (Exception e) {
            throw new CommentException(HttpStatus.BAD_REQUEST, "댓글 작성 실패");
        }
    }

    @Override
    public void writePostReply(Long postId, Long commentId, CommentRequestDTO commentRequestDTO) {
        Long userId = communityAuthService.getUserId();
        communityAuthService.checkUserValidity(userId);

        CommentVO parentCheck = new CommentVO();
        parentCheck.setId(commentId);
        parentCheck.setPostId(postId);
        if (commentDAO.existByIdAndPostId(parentCheck) == 0) {
            throw new CommentException(HttpStatus.BAD_REQUEST, "해당 게시글에 존재하지 않는 댓글입니다.");
        }

        CommentVO commentVO = CommentVO.from(commentRequestDTO);
        commentVO.setPostId(postId);
        commentVO.setUserId(userId);
        commentVO.setCommentId(commentId);

        try {
            commentDAO.save(commentVO);
        } catch (Exception e) {
            throw new CommentException(HttpStatus.BAD_REQUEST, "대댓글 작성 실패");
        }
    }

    @Override
    public void updateComment(Long commentId, CommentRequestDTO commentRequestDTO) {
        Long userId = communityAuthService.getUserId();

        CommentVO commentVO = new CommentVO();
        commentVO.setId(commentId);
        commentVO.setUserId(userId);

        if (commentDAO.existByIdAndUserId(commentVO) == 0) {
            throw new CommentException(HttpStatus.BAD_REQUEST, "해당 댓글 수정 권한 없습니다.");
        }

        commentVO.setCommentContent(commentRequestDTO.getCommentContent());
        commentDAO.update(commentVO);
    }

    @Override
    public void deleteComment(Long commentId) {
        Long userId = communityAuthService.getUserId();

        CommentVO commentVO = new CommentVO();
        commentVO.setId(commentId);
        commentVO.setUserId(userId);

        if (commentDAO.existByIdAndUserId(commentVO) == 0) {
            log.info("해당 부분 예외: {}", commentVO.getId().toString());
            throw new CommentException(HttpStatus.BAD_REQUEST, "해당 댓글 삭제 권한 없습니다.");
        }

        if (commentDAO.isParentComment(commentId) > 0) {
            commentDAO.updateRepliesIsDeleted(commentId);
        }

        commentDAO.updateIsDeleted(commentVO);
    }

//    댓글 좋아요 남기기
    @Override
    public void addCommentLike(Long commentId) {
        Long userId = communityAuthService.getUserId();
        communityAuthService.checkUserValidity(userId);

        CommentLikeVO  commentLikeVO = new CommentLikeVO();
        commentLikeVO.setCommentId(commentId);
        commentLikeVO.setUserId(userId);

        try {
            commentLikeDAO.save(commentLikeVO);
        } catch (Exception e) {
            throw new CommentException(HttpStatus.BAD_REQUEST, "댓글 좋아요 추가 실패");
        }
    }

//    좋아요 취소
    @Override
    public void cancelCommentLike(Long commentId) {
        Long userId = communityAuthService.getUserId();
        communityAuthService.checkUserValidity(userId);

//        DAO 에 전달 할 VO 제작
        CommentLikeVO commentLikeVO = new CommentLikeVO();
        commentLikeVO.setCommentId(commentId);
        commentLikeVO.setUserId(userId);

//        DAO 에 VO 전달하여 DB 접근하여 조작
        try {
            commentLikeDAO.deleteByUserIdAndCommentId(commentLikeVO);
        } catch (Exception e) {
            throw new CommentException(HttpStatus.BAD_REQUEST, "댓글 좋아요 취소 실패");
        }
    }
}
