package com.app.springapp.service.edu;

import com.app.springapp.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class EduVideoServiceTests {

    @Autowired
    private EduVideoService eduVideoService;

    @Test
    public void getEduVideoByIdTest() {
        log.info("video: {}", eduVideoService.getEduVideoById(1L));
    }
}
