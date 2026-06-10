package com.app.springapp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserBlockServiceTest {
    @Autowired
    private UserBlockService userBlockService;

//    유저 차단 테스트
    @Test
    public void blockUserTest() {
        Long userId = 1L;
        Long blockingId = 4L;

        userBlockService.blockUser(userId, blockingId);
    }
}
