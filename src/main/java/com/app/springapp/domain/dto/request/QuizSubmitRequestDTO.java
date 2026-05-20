package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@Schema(description = "퀴즈 제출 요청 DTO")
public class QuizSubmitRequestDTO {
    @Schema(description = "사용자 번호", example = "1", required = true)
    private Long userId;

    @Schema(description = "제출 답안 목록", required = true)
    private List<QuizSubmitAnswerRequestDTO> answers;
}
