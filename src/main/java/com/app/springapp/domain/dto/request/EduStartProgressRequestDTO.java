package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@Schema(description = "학습 세션 문제 정답 결과 요청 DTO")
public class EduStartProgressRequestDTO {
    @Schema(description = "문제 번호", example = "1")
    private int questionNumber;

    @Schema(description = "정답 여부: 정답이면 1, 오답이면 0", example = "1")
    private int isCorrect;
}