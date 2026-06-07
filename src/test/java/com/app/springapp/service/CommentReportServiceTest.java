package com.app.springapp.service;

import com.app.springapp.domain.dto.request.CommentReportRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CommentReportServiceTest {
    @Autowired
    CommentReportService commentReportService;

    @Test
    public void reportCommentTest() {
        Long userId = 1L;
        CommentReportRequestDTO commentReportRequestDTO = new CommentReportRequestDTO();
        commentReportRequestDTO.setCommentReportTitle("test");
        commentReportRequestDTO.setCommentReportDetail("test");
        commentReportRequestDTO.setCommentId(1L);

        commentReportService.reportComment(userId, commentReportRequestDTO);
    }
}
