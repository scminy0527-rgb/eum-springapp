package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "수어 검색 응답 DTO")
public class SignWordResponseDTO {

    @Schema(description = "수어 데이터 번호", example = "1")
    private Long id;

    @Schema(description = "수어 단어명", example = "안녕하세요")
    private String signWordTitle;

    @Schema(description = "수어 설명", example = "오른손을...")
    private String signWordDescription;

    @Schema(description = "카테고리", example = "식생활")
    private String signWordCategory;

    @Schema(description = "썸네일 이미지 URL", example = "https://sldict.korean.go.kr/...")
    private String signWordThumbnailUrl;

    @Schema(description = "수어 영상 URL", example = "https://sldict.korean.go.kr/...")
    private String signWordVideoUrl;

    @Schema(description = "수어 이미지 URL 목록", example = "https://...,https://...")
    private String signWordImages;

    @Schema(description = "원본 상세 페이지 URL", example = "https://sldict.korean.go.kr/...")
    private String signWordSourceUrl;

    @Schema(description = "단어 이모지", example = "🙏")
    private String emoji;
}
