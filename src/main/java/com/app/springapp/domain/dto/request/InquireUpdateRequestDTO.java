package com.app.springapp.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "문의 수정 요청")
@Data
public class InquireUpdateRequestDTO {

    @Schema(description = "문의 내용", example = "내용 수정합니다")
    private String inquireContent;
}