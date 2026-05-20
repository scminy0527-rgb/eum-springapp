package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Schema(description = "퀴즈 제출 답안 요청 DTO")
public class QuizSubmitAnswerRequestDTO {
    @Schema(description = "퀴즈 문제 번호", example = "1", required = true)
    private Long quizQuestionId;

    @Schema(description = "선택한 보기 번호", example = "3", required = true)
    private Long quizChoiceId;

}
