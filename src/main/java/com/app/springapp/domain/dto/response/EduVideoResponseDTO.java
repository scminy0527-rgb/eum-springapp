package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.EduVideoDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "수어 영상 응답 DTO")
public class EduVideoResponseDTO {
    @Schema(description = "수어 영상 번호", example = "1")
    private Long id;
    @Schema(description = "영상 제목", example = "안녕하세요 수어 영상")
    private String eduVideoTitle;
    @Schema(description = "영상 설명", example = "안녕하세요를 표현하는 수어입니다.")
    private String eduVideoDetail;
    @Schema(description = "영상 타입", example = "mp4")
    private String eduVideoType;
    @Schema(description = "영상 URL", example = "https://example.com/video.mp4")
    private String eduVideoUrl;
    @Schema(description = "영상 썸네일 URL", example = "/assets/image/fairy-tale/story01.jpg")
    private String eduVideoThumbnailUrl;
    @Schema(description = "영상 업로드 일시", example = "2024-01-01T00:00:00")
    private LocalDateTime eduVideoCreateAt;

    public static EduVideoResponseDTO from(EduVideoDTO dto) {
        EduVideoResponseDTO res = new EduVideoResponseDTO();
        res.setId(dto.getId());
        res.setEduVideoTitle(dto.getEduVideoTitle());
        res.setEduVideoDetail(dto.getEduVideoDetail());
        res.setEduVideoType(dto.getEduVideoType());
        res.setEduVideoUrl(dto.getEduVideoUrl());
        res.setEduVideoThumbnailUrl(dto.getEduVideoThumbnailUrl());
        res.setEduVideoCreateAt(dto.getEduVideoCreateAt());
        return res;
    }
}
