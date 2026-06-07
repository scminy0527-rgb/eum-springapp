package com.app.springapp.repository;

import com.app.springapp.domain.dto.PostDTO;
import com.app.springapp.domain.vo.PostVO;
import com.app.springapp.mapper.PostMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostDAO {

    private final PostMapper postMapper;

//    게시글 전체 불러오는 매서드
    public List<PostDTO> findAll(Map<String, Object> filters) {
        return postMapper.selectAll(filters);
    }

//    특정 게시글 불러오는 매서드
    public Optional<PostDTO> findById(PostDTO postDTO) {
        return Optional.ofNullable(postMapper.select(postDTO));
    }

//    유저 프로필 에서 유저 작성한 게시글 목록
    public List<PostDTO> findByUserId(Map<String, Object> filters) {
        return postMapper.selectByUserId(filters);
    }

//    유저가 좋아한 글 불러오는 코드
    public List<PostDTO> findByUserPostLike(Map<String, Object> filters) {
        return postMapper.selectByUserPostLike(filters);
    }

//    유저가 좋아요 한 게시글 전체 갯수
    public int countByUserPostLike(Long userId) {
        return postMapper.countByUserPostLike(userId);
    }

//    불러올 게시글의 전체 갯수
    public int findCount(Map<String, Object> filters) {
        return postMapper.selectCount(filters);
    }

//    유저가 작성한 게시글 전체 갯수
    public int countByUserId(Long userId) {
        return postMapper.countByUserId(userId);
    }

//    유저가 해당 게시글을 작성했어서 수정에 대한 권한이 있는지?
    public int existByIdAndUserId(PostVO postVO) {
        return postMapper.existByIdAndUserId(postVO);
    }

//    포스트 작성
    public void save(PostVO postVO) {
        postMapper.insert(postVO);
    }

//    게시글 수정
    public void update(PostVO postVO) {
        postMapper.update(postVO);
    }

//    게시글 조회수 1 증가
    public void updatePostReadCount(Long id) {
        postMapper.updatePostReadCount(id);
    }

//    게시글 삭제
    public void updatePostIsDeleted(PostVO postVO) {
        postMapper.updatePostIsDeleted(postVO);
    }

// 게시글 작성자 ID 조회
    public Long findOwnerIdByPostId(Long postId) {
        return postMapper.selectOwnerIdByPostId(postId);
    }
}
