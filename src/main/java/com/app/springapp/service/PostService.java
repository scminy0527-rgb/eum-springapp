package com.app.springapp.service;

import com.app.springapp.domain.dto.PostDTO;
import com.app.springapp.domain.dto.request.PostRequestDTO;
import com.app.springapp.domain.dto.response.PostSelectResponseDTO;

import java.util.Map;

public interface PostService {
//    게시글 전체 리스트 불러오기
    public Map<String, Object> getAllPosts(Map<String, Object> filters);

//    특정 게시글 불러오기
    public PostSelectResponseDTO getPost(Long id, Long userId);

//    유저가 작성 한 게시글들 불러오기
    public Map<String, Object> getUserPosts(Long userId, Map<String, Object> filters);

//    유저가 좋아요 한 게시글 불러오기
    public Map<String, Object> getUserLikedPosts(Long userId, Map<String, Object> filters);

//    게시글 작성
    public void writePost(Long userId, PostRequestDTO postRequestDTO);

//    게시글 수정
    public void updatePost(Long userId, Long id, PostRequestDTO postRequestDTO);

//    게시글 삭제
    public void deletePost(Long userId, Long id);

//    게시글 접근권한 확인
    public boolean canTouchPost(Long id, Long userId);

//    게시글 조회수 증가
    public void increasePostReadCount(Long id);

//    게시글 좋아요 증가
    public void increasePostLikeCount(Long userId, Long postId);

//    게시글 좋아요 삭제
    public void cancelPostLike(Long userId, Long postId);

//    페이지 계산
    public int calcTotalPages(int totalCount, int size);
}
