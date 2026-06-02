package com.app.springapp.service;

import com.app.springapp.domain.dto.PostDTO;
import com.app.springapp.domain.dto.request.PostRequestDTO;
import com.app.springapp.domain.dto.response.PostResponseDTO;
import com.app.springapp.domain.dto.response.PostSelectResponseDTO;
import com.app.springapp.domain.vo.PostLikeVO;
import com.app.springapp.domain.vo.PostVO;
import com.app.springapp.exception.PostException;
import com.app.springapp.repository.CommentDAO;
import com.app.springapp.repository.PostDAO;
import com.app.springapp.repository.PostLikeDAO;
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
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, PostException.class})
public class PostServiceImpl implements PostService {
    private final PostDAO postDAO;
    private final CommunityAuthService communityAuthService;
    private final PostLikeDAO postLikeDAO;
    private final CommentDAO commentDAO;
    private final UserExpService userExpService;

    @Override
    public Map<String, Object> getAllPosts(Map<String, Object> req) {
        int page = (Integer) req.get("page");
        int size = 4;
        int offset = (page - 1) * size;
        String postTag = (String) req.get("postTag");
        String keyword = (String) req.get("keyword");

        Map<String, Object> filters = new HashMap<>();
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("postTag", postTag);
        filters.put("keyword", keyword);

        List<PostResponseDTO> posts = postDAO.findAll(filters).stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());

        int postCounts = postDAO.findCount(filters);

        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("currentPage", page);
        result.put("totalPages", this.calcTotalPages(postCounts, size));
        result.put("size", size);
        result.put("postCounts", postCounts);

        return result;
    }

//    특정 게시글 조회
    @Override
    public PostSelectResponseDTO getPost(Long id) {
        Long userId = communityAuthService.getUserId();

        PostDTO postDTO = new PostDTO();
        postDTO.setId(id);
        postDTO.setUserId(userId);

//        게시글 불러오기 (없다면 예외)
        PostDTO post = postDAO.findById(postDTO).orElseThrow(() -> {
            throw new PostException(HttpStatus.BAD_REQUEST, "포스트 불러오기 실패");
        });

//        존재 확인 후 조회수 증가
        this.increasePostReadCount(id);

        return PostSelectResponseDTO.from(post);
    }

//    특정 유저의 프로필 에서 해당 유저가 작성 한 모든 게시글 보여주기
    @Override
    public Map<String, Object> getUserPosts(Long userId, Map<String, Object> req) {
        int page = (Integer) req.get("page");
        int size = 4;
        int offset = (page - 1) * size;
        String order =  (String) req.get("order");
        String keyword = (String) req.get("keyword");

        Map<String, Object> filters = new HashMap<>();
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("userId", userId);
        filters.put("order", order);
        filters.put("keyword", keyword);

        List<PostResponseDTO> posts = postDAO.findByUserId(filters).stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());

        int postCounts = postDAO.countByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("currentPage", page);
        result.put("totalPages", this.calcTotalPages(postCounts, size));
        result.put("size", size);
        result.put("postCounts", postCounts);

        return result;
    }

//    유저가 좋아요 한 게시글 목록 불러오기
    @Override
    public Map<String, Object> getUserLikedPosts(Long userId, Map<String, Object> req) {
        int page = (Integer) req.get("page");
        int size = 4;
        int offset = (page - 1) * size;
        String order =  (String) req.get("order");
        String keyword = (String) req.get("keyword");

        Map<String, Object> filters = new HashMap<>();
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("userId", userId);
        filters.put("order", order);
        filters.put("keyword", keyword);

        List<PostResponseDTO> posts = postDAO.findByUserPostLike(filters).stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());

        int postCounts = postDAO.countByUserPostLike(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("currentPage", page);
        result.put("totalPages", this.calcTotalPages(postCounts, size));
        result.put("size", size);
        result.put("postCounts", postCounts);

        return result;
    }

//    게시글 작성
    @Override
    public void writePost(PostRequestDTO postRequestDTO) {
        Long userId = communityAuthService.getUserId();

        PostVO postVO = PostVO.from(postRequestDTO);
        postVO.setUserId(userId);

        try {
            postDAO.save(postVO);

            //  게시글 작성 경험치 지급
            userExpService.addPostExp(userId, postVO.getId());
        } catch (Exception e) {
            throw new PostException(HttpStatus.BAD_REQUEST, "게시글 작성 실패");
        }
    }

//    게시글 수정
    @Override
    public void updatePost(Long id, PostRequestDTO postRequestDTO) {
        Long userId = communityAuthService.getUserId();
        PostVO postVO = PostVO.from(postRequestDTO);
        postVO.setId(id);
        postVO.setUserId(userId);

        if(canTouchPost(id, userId)){
            postDAO.update(postVO);
        } else {
            throw new PostException(HttpStatus.BAD_REQUEST, "해당 게시글 수정 권한 없습니다.");
        }
    }

//    게시글 삭제 (게시글에 있는 모든 댓글도 삭제)
    @Override
    public void deletePost(Long id) {
        Long userId = communityAuthService.getUserId();
        if(canTouchPost(id, userId)){
            PostVO postVO = new PostVO();
            postVO.setId(id);
            postVO.setUserId(userId);

//            게시글 내 댓글 소프트삭제
            commentDAO.updateDeleteByPostId(id);

//            게시글 소프트 삭제
            postDAO.updatePostIsDeleted(postVO);
        } else {
            throw new PostException(HttpStatus.BAD_REQUEST, "해당 게시글 삭제 권한 없습니다.");
        }
    }

    @Override
    public int calcTotalPages(int totalCount, int size) {
        return (int) Math.ceil((double) totalCount / size);
    }

//    유저가 해당 게시글 접근 권한 있는지 확인
    @Override
    public boolean canTouchPost(Long id, Long userId) {
        PostVO postVO = new PostVO();
        postVO.setId(id);
        postVO.setUserId(userId);

        return postDAO.existByIdAndUserId(postVO) != 0;
    }

//    게시글 조회수 1 증가
    @Override
    public void increasePostReadCount(Long id) {
        postDAO.updatePostReadCount(id);
    }

//    게시글 좋아요 증가 시키기
    @Override
    public void increasePostLikeCount(Long postId) {
        Long userId = communityAuthService.getUserId();

//        유효성 검증
        communityAuthService.checkUserValidity(userId);

        PostLikeVO postLikeVO = new PostLikeVO();
        postLikeVO.setPostId(postId);
        postLikeVO.setUserId(userId);

        try {
            postLikeDAO.save(postLikeVO);
        } catch (Exception e) {
            throw new PostException(HttpStatus.BAD_REQUEST, "해당 게시글에 좋아요 할 수 없습니다.");
        }
    }

//    게시글 좋아요 삭제
    @Override
    public void cancelPostLike(Long postId) {
        Long userId = communityAuthService.getUserId();
        communityAuthService.checkUserValidity(userId);

        PostLikeVO postLikeVO = new PostLikeVO();
        postLikeVO.setPostId(postId);
        postLikeVO.setUserId(userId);

        try {
            postLikeDAO.deleteByUserIdAndPostId(postLikeVO);
        } catch (Exception e) {
            throw new PostException(HttpStatus.BAD_REQUEST, "해당 게시글 좋아요 취소 불가능");
        }
    }
}
