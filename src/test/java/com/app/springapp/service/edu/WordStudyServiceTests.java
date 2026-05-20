package com.app.springapp.service.edu;

import com.app.springapp.domain.vo.WordStudyVO;
import com.app.springapp.service.WordStudyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class WordStudyServiceTests {
    @Autowired
    private WordStudyService wordStudyService;

    @Test
    public void finishWordStudyTest() {
        WordStudyVO wordStudyVO = new WordStudyVO();
        wordStudyVO.setUserId(1L);
        wordStudyVO.setEduWordMapId(1L);

        wordStudyService.finishWordStudy(wordStudyVO);
    }

    @Test
    public void getWordStudyTest() {
        log.info("wordStudy: {}", wordStudyService.getWordStudy(1L, 1L));
    }

    @Test
    public void getCompletedWordCountTest() {
        log.info("completed count: {}", wordStudyService.getCompletedWordCount(1L, 1L));
    }

    @Test
    public void getTotalWordCountTest() {
        log.info("total count: {}", wordStudyService.getTotalWordCount(1L));
    }

}
