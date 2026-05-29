package com.app.springapp.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WordsVO {
    private Long id;
    private String wordsTitle;
    private String wordsDetail;
    private String wordsImage;
    private String wordsType;
    private Long eduVideoId;
    private Long signWordId;
}
