package com.app.springapp.service;

import com.app.springapp.domain.dto.NoticeDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class NoticeServiceTests {
    @Autowired
    private NoticeService noticeService;

    @Test
    public void testSave() {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeTitle("테스트 제목");
        noticeDTO.setNoticeContent("테스트 내용");
        noticeDTO.setNoticeCategory("공지");
        noticeDTO.setNoticePinned(0);
        noticeDTO.setNoticeFileUrl("default.jpg");
        noticeDTO.setUserId(1L);

        noticeService.save(noticeDTO);
        log.info("등록 성공");
    }

    @Test
    public void testFindAll() {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setOffset(0);
        noticeDTO.setSize(10);

        var list = noticeService.findAllNotices(noticeDTO);
        log.info("목록 조회 결과: {}", list);
    }

    @Test
    public void testFindById() {
        NoticeDTO notice = noticeService.findNoticeById(3L);
        log.info("상세 조회 결과: {}", notice);
    }


    @Test
    public void testUpdate() {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setId(3L);
        noticeDTO.setNoticeTitle("수정된 제목");
        noticeDTO.setNoticeContent("수정된 내용");
        noticeDTO.setNoticeCategory("업데이트");
        noticeDTO.setNoticePinned(0);
        noticeDTO.setNoticeFileUrl("default.jpg");

        noticeService.update(noticeDTO);
        log.info("수정 성공");
    }

    @Test
    public void testDelete() {
        noticeService.delete(3L);
        log.info("삭제 성공");
    }
}
