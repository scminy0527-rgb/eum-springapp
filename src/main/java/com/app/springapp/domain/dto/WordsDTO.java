package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "수어 단어 DTO")
public class WordsDTO {
    @Schema(description = "단어 번호", example = "1")
    private Long id;
    @Schema(description = "단어 제목", example = "안녕하세요")
    private String wordsTitle;
    @Schema(description = "단어 설명", example = "인사할 때 사용하는 수어입니다.")
    private String wordsDetail;
    @Schema(description = "단어 이미지", example = "default.jpg")
    private String wordsImage;
    @Schema(description = "단어 타입", example = "인사")
    private String wordsType;
    @Schema(description = "수어 영상 번호", example = "1")
    private Long eduVideoId;
    @Schema(description = "OpenAPI 수어 단어 번호", example = "5")
    private Long signWordId;

}
