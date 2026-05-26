package com.app.springapp.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
@Schema(description = "자격증 갱신 DTO")
public class CertRenewDTO {
    @Schema(description = "갱신 번호")
    private Long id;
    @Schema(description = "갱신 일시")
    private LocalDateTime certRenewCreateAt;
    @Schema(description = "시험 결과 번호")
    private Long testResultId;
    @Schema(description = "유저 번호")
    private Long userId;
    @Schema(description = "신청 유형 (renew/reissue)")
    private String certRenewType;
    @Schema(description = "수령 방법 (online/delivery)")
    private String certReceiveType;
    @Schema(description = "자격증 번호")
    private String certNo;
    @Schema(description = "성명")
    private String certName;
    @Schema(description = "연락처")
    private String certPhone;
    @Schema(description = "배송 주소")
    private String certAddress;
}
