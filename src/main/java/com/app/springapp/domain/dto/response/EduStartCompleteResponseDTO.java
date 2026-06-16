package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "학습 세션 완료 결과 응답 DTO")
public class EduStartCompleteResponseDTO {
    @Schema(description = "정답 수", example = "4")
    private int correctCount;

    @Schema(description = "전체 문제 수", example = "5")
    private int totalCount;

    @Schema(description = "정답률", example = "80")
    private int accuracy;

    @Schema(description = "학습 시간: 초 단위", example = "140")
    private int spentTime;

    @Schema(description = "획득 경험치", example = "80")
    private int exp;
}
