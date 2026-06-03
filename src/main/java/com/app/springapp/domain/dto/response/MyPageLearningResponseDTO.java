package com.app.springapp.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@Schema(description = "마이페이지 학습 페이지 응답 DTO")
public class MyPageLearningResponseDTO {
    @Schema(description = "학습현황 목록", required = true)
    private List<MyPageLearningStatusResponseDTO> statusList;

    @Schema(description = "학습결과 목록", required = true)
    private List<MyPageLearningResultResponseDTO> resultList;

    @Schema(description = "학습요약", required = true)
    private MyPageLearningSummaryResponseDTO summary;
}