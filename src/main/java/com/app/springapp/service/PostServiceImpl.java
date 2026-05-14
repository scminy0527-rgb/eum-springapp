package com.app.springapp.service;

import com.app.springapp.domain.dto.PostDTO;
import com.app.springapp.domain.dto.request.PostRequestDTO;
import com.app.springapp.domain.dto.response.PostResponseDTO;
import com.app.springapp.domain.dto.response.PostSelectResponseDTO;
import com.app.springapp.domain.vo.PostVO;
import com.app.springapp.exception.PostException;
import com.app.springapp.repository.PostDAO;
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

    @Override
    public Map<String, Object> getAllPosts(Map<String, Object> req) {
        int page = (Integer) req.get("page");
        int size = 4;
        int offset = (page - 1) * size;
        String postTag = (String) req.get("postTag");

        Map<String, Object> filters = new HashMap<>();
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("postTag", postTag);

        List<PostResponseDTO> posts = postDAO.findAll(filters).stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());

        int postCounts = postDAO.findCount(postTag);

        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("currentPage", page);
        result.put("totalPages", calcTotalPages(postCounts, size));
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

        Map<String, Object> filters = new HashMap<>();
        filters.put("size", size);
        filters.put("offset", offset);
        filters.put("userId", userId);

        List<PostResponseDTO> posts = postDAO.findByUserId(filters).stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());

        int postCounts = postDAO.countByUserId(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("currentPage", page);
        result.put("totalPages", calcTotalPages(postCounts, size));
        result.put("size", size);
        result.put("postCounts", postCounts);

        return result;
    }

//    게시글 작성
    @Override
    public void writePost(PostRequestDTO postRequestDTO) {
        PostVO postVO = PostVO.from(postRequestDTO);
        postVO.setUserId(communityAuthService.getUserId());
        try {
            postDAO.save(postVO);
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

//    게시글 삭제
    @Override
    public void deletePost(Long id) {
        Long userId = communityAuthService.getUserId();
        if(canTouchPost(id, userId)){
            PostVO postVO = new PostVO();
            postVO.setId(id);
            postVO.setUserId(userId);
            postDAO.updatePostIsDeleted(postVO);
        } else {
            throw new PostException(HttpStatus.BAD_REQUEST, "해당 게시글 삭제 권한 없습니다.");
        }
    }

    private int calcTotalPages(int totalCount, int size) {
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
}
