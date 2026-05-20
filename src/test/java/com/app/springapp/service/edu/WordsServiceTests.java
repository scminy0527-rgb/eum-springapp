package com.app.springapp.service.edu;

import com.app.springapp.service.WordsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class WordsServiceTests {
    @Autowired
    private WordsService wordsService;

    @Test
    public void getWordsByEduIdTest() {
        wordsService.getWordsByEduId(1L).forEach(word -> log.info("word: {}", word));
    }

}
