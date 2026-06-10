package com.app.springapp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FollowServiceTest {
    @Autowired
    private FollowService followService;

//    팔로우 테스트
    @Test
    public void userFollowTest(){
        followService.userFollow(1L, 10L);
    }

//    팔로우 취소 테스트
    @Test
    public void cancelFollowTest(){
        followService.cancelFollow(1L, 7L);
    }
}
