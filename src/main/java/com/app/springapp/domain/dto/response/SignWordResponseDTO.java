package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "문화공공데이터 수어 응답 DTO")
public class SignWordResponseDTO {

    @Schema(description = "수어 단어명", example = "안녕하세요")
    private String title;

    @Schema(description = "썸네일 이미지 URL", example = "http://sldict.korean.go.kr/...")
    private String thumbnailUrl;

    @Schema(description = "수어 영상 URL", example = "http://sldict.korean.go.kr/...")
    private String videoUrl;

    @Schema(description = "수어 설명", example = "오른손을...")
    private String signDescription;

    @Schema(description = "수어 이미지 URL 목록", example = "http://...,http://...")
    private String signImages;

    @Schema(description = "카테고리", example = "식생활")
    private String categoryType;

    @Schema(description = "원본 상세 페이지 URL", example = "http://sldict.korean.go.kr/...")
    private String sourceUrl;

}
