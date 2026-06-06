package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import com.app.springapp.domain.dto.QuizQuestionDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Data
@Schema(description = "퀴즈 문제 응답 DTO")
public class QuizQuestionResponseDTO {
    @Schema(description = "문제 번호", example = "1")
    private Long id;
    @Schema(description = "문제 타입", example = "객관식")
    private String quizQuestionType;
    @Schema(description = "문제 내용", example = "다음 수어는 무엇을 의미하나요?")
    private String quizQuestionDetail;
    @Schema(description = "문제 생성일시", example = "2024-01-01T00:00:00")
    private LocalDateTime quizQuestionCreateAt;
    @Schema(description = "수어 단어 번호", example = "1")
    private Long wordsId;
    @Schema(description = "문제 보기 목록")
    private List<QuizChoiceResponseDTO> choices;

    public static QuizQuestionResponseDTO from(QuizQuestionDTO dto) {
        QuizQuestionResponseDTO res = new QuizQuestionResponseDTO();
        res.setId(dto.getId());
        res.setQuizQuestionType(dto.getQuizQuestionType());
        res.setQuizQuestionDetail(dto.getQuizQuestionDetail());
        res.setQuizQuestionCreateAt(dto.getQuizQuestionCreateAt());
        res.setWordsId(dto.getWordsId());
        return res;
    }
}
