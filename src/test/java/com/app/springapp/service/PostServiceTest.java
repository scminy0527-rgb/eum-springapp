package com.app.springapp.service;

import com.app.springapp.domain.dto.PostDTO;
import com.app.springapp.domain.dto.request.PostRequestDTO;
import com.app.springapp.domain.dto.response.PostResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 커뮤니티 서비스 중 포스트 관련 서비스 테스트 위한 단테 파일

@SpringBootTest
@Slf4j
public class PostServiceTest {

    @Autowired
    private PostService postService;

//    게시글 리스트 전체 불러오는 서비스 테스트
    @Test
    public void getAllPostsTest(){
        log.info("전체 게시글 불러오는 관점");
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> map2 = new HashMap<>();

        map1.put("page",2);
//        postService.getAllPosts(map1).stream()
//                .forEach((post) -> log.info(post.toString()));
        Map<String,Object> result1 = postService.getAllPosts(map1);
        result1.forEach((k,v)->{
           log.info("Key: {}", k);
           log.info("Value: {}", v);
        });

        map2.put("page",1);
        map2.put("postTag","자유게시판");

//        log.info("자유게시판 불러오기");
//        postService.getAllPosts(map2).stream()
//                .forEach((post) -> log.info(post.toString()));
    }

//    단위 개시글 불러오기 테스트
    @Test
    public void getPostTest(){
        Long id=1L;

        log.info(postService.getPost(id).toString());
    }

//    유저의 특정 게시글 불러오게 하기
    @Test
    public void getUserPostsTest(){
        Map<String,Object> req = new HashMap<>();
        req.put("page",1);

        Map<String, Object> result = postService.getUserPosts(5L, req);

        result.forEach((k,v)->{
            log.info("유저 게시글 Key: {}", k);
            log.info("유저 게시글 Value: {}", v);
        });
    }

//    유저가 좋아요 한 게시글 불러오기 서비스 테스트
    @Test
    public void getUserLikedPostsTest(){
        Map<String,Object> req = new HashMap<>();
        req.put("page",1);
        Long UserId=1L;

        Map<String,Object> result = postService.getUserLikedPosts(UserId,req);
        List<PostResponseDTO> posts = (List<PostResponseDTO>) result.get("posts");
        posts.stream()
                .forEach(post->{
                    log.info(post.toString());
                });
    }

//    게시글 작성 테스트
    @Test
    public void writePostTest(){
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setPostTitle("수어로 노래를 불렀어요");
        postRequestDTO.setPostContent("수어로도 노래를 할 수 있는게 신기해요");
        postRequestDTO.setPostTag("자유게시판");
        Long userId = 3L;

        postService.writePost(postRequestDTO);
    }

//    게시글 접근권한 테스트
    @Test
    public void canTouchPostTest(){
        Long id = 42L;
        Long userId = 4L;

        boolean result = postService.canTouchPost(id, userId);
        log.info("접근 가능 여부: {}", result);
    }

//    게시글 수정 테스트
    @Test
    public void updatePostTest(){
        Long id = 21L;
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setPostTitle("점자란");
        postRequestDTO.setPostContent("만국 공통 입니다.");

        postService.updatePost(id, postRequestDTO);
    }

//    게시글 삭제 테스트
    @Test
    public void deletePostTest(){
        Long id = 1L;
        postService.deletePost(id);
    }

//    게시글 조회수 증가 테스트
    @Test
    public void increasePostReadCountTest(){
        Long id = 61L;
        postService.increasePostReadCount(id);
    }

//    게시글 좋아요 테스트 (정상 작동)
    @Test
    public void increasePostLikeCountTest(){
        Long id = 19L;
        postService.increasePostLikeCount(id);
    }

//    게시글 좋아요 취소테스트
    @Test
    public void cancelPostLikeTest(){
        Long id = 2L;
        postService.cancelPostLike(id);
    }
}
