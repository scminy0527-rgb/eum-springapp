package com.app.springapp.service;

import com.app.springapp.domain.dto.InquireDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class InquireServiceTests {

    @Autowired
    private InquireService inquireService;

    // 등록 테스트
    @Test
    public void testSave() {
        InquireDTO inquireDTO = new InquireDTO();
        inquireDTO.setInquireType("학습 문의");
        inquireDTO.setInquireEmail("test@gmail.com");
        inquireDTO.setInquireTitle("테스트 문의 제목");
        inquireDTO.setInquireContent("테스트 문의 내용입니다.");
        inquireDTO.setInquireFileUrl("default.jpg");
        inquireDTO.setUserId(1L);

        inquireService.save(inquireDTO);
        log.info("등록 성공");
    }

    // 목록 조회 테스트
    @Test
    public void testFindAll() {
        InquireDTO inquireDTO = new InquireDTO();
        inquireDTO.setUserId(1L);

        var list = inquireService.findAllInquires(inquireDTO);
        log.info("목록 조회 결과: {}", list);
    }

    // 상세 조회 테스트
    @Test
    public void testFindById() {
        InquireDTO inquire = inquireService.findInquireById(1L);
        log.info("상세 조회 결과: {}", inquire);
    }

    // 삭제 테스트
    @Test
    public void testDelete() {
        inquireService.delete(1L);
        log.info("삭제 성공");
    }

    @Test
    public void testUpdateContent() {
        InquireDTO inquireDTO = new InquireDTO();
        inquireDTO.setId(1L);
        inquireDTO.setInquireTitle("수정된 제목");
        inquireDTO.setInquireContent("수정된 내용");

        inquireService.updateContent(inquireDTO);
        log.info("수정성공");
    }
}