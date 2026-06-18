package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "수어 영상 등록 요청 DTO")
public class EduVideoRequestDTO {
    @Schema(description = "영상 제목", example = "안녕하세요 수어 영상", required = true)
    private String eduVideoTitle;
    @Schema(description = "영상 설명", example = "안녕하세요를 표현하는 수어입니다.", required = true)
    private String eduVideoDetail;
    @Schema(description = "영상 타입", example = "mp4", required = true)
    private String eduVideoType;
    @Schema(description = "영상 URL", example = "https://example.com/video.mp4", required = true)
    private String eduVideoUrl;
    @Schema(description = "영상 썸네일 URL", example = "/assets/image/fairy-tale/story01.jpg")
    private String eduVideoThumbnailUrl;
}
