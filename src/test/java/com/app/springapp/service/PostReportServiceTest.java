package com.app.springapp.service;

import com.app.springapp.domain.dto.request.PostReportRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PostReportServiceTest {
    @Autowired
    PostReportService postReportService;

    @Test
    public void reportPostTest() {
        Long userId = 1L;
        PostReportRequestDTO postReportRequestDTO = new PostReportRequestDTO();
        postReportRequestDTO.setPostReportTitle("test");
        postReportRequestDTO.setPostReportDetail("test");
        postReportRequestDTO.setPostId(1L);

        postReportService.reportPost(userId, postReportRequestDTO);
    }
}
