package com.app.springapp.service;

import com.app.springapp.domain.dto.request.UserReportRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class UserReportServiceTest {
    @Autowired
    UserReportService userReportService;

//    유저 신고 단위테스트
    @Test
    public void reportUserTest() {
        Long userId = 1L;
        UserReportRequestDTO userReportRequestDTO = new UserReportRequestDTO();
        userReportRequestDTO.setUserReportTitle("test");
        userReportRequestDTO.setUserReportDetail("test");
        userReportRequestDTO.setReportingUserId(3L);

        userReportService.reportUser(userId, userReportRequestDTO);
    }
}
