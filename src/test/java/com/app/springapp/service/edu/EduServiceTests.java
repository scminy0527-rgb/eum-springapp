package com.app.springapp.service.edu;

import com.app.springapp.service.EduService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class EduServiceTests {
    @Autowired
    private EduService eduService;

    @Test
    public void getEduListTest() {
        eduService.getEduList().forEach(edu -> log.info("edu: {}", edu));
    }

    @Test
    public void getEduDetailByIdTest() {
        log.info("edu: {}", eduService.getEduDetailById(1L));
    }
}
