package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.EduDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "수어 학습 응답 DTO")
public class EduResponseDTO {
    @Schema(description = "학습 번호", example = "1")
    private Long id;
    @Schema(description = "학습 제목", example = "기초 수어 학습")
    private String eduTitle;
    @Schema(description = "학습 설명", example = "기초 수어를 배울 수 있습니다.")
    private String eduDetail;
    @Schema(description = "다이아 가격", example = "0")
    private int eduDia;
    @Schema(description = "삭제 여부 (0: 미삭제, 1: 삭제)", example = "0")
    private int eduIsDeleted;

    public static EduResponseDTO from(EduDTO dto) {
        EduResponseDTO res = new EduResponseDTO();
        res.setId(dto.getId());
        res.setEduTitle(dto.getEduTitle());
        res.setEduDetail(dto.getEduDetail());
        res.setEduDia(dto.getEduDia());
        res.setEduIsDeleted(dto.getEduIsDeleted());
        return res;
    }
}
