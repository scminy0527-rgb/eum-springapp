package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.EduCertDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "학습 수료증 응답 DTO")
public class EduCertResponseDTO {
    @Schema(description = "수료증 번호", example = "1")
    private Long id;
    @Schema(description = "수료증 발급일시", example = "2024-01-01T00:00:00")
    private LocalDateTime eduCertCreateAt;
    @Schema(description = "수료증 만료일시 (발급일 + 180일)")
    private LocalDateTime eduCertExpireAt;
    @Schema(description = "학습 번호", example = "1")
    private Long eduId;
    @Schema(description = "학습 과정명", example = "수어통역 기초과정")
    private String eduTitle;
    @Schema(description = "유저 번호", example = "1")
    private Long userId;
}
