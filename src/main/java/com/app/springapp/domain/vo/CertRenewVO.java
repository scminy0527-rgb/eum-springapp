package com.app.springapp.domain.vo;

import com.app.springapp.domain.dto.CertRenewDTO;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Data
public class CertRenewVO {
    private Long id;
    private LocalDateTime certRenewCreateAt;
    private Long testResultId;
    private Long userId;
    private String certRenewType;
    private String certReceiveType;
    private String certNo;
    private String certName;
    private String certPhone;
    private String certAddress;

    public static CertRenewVO from(CertRenewDTO dto) {
        CertRenewVO vo = new CertRenewVO();
        vo.setTestResultId(dto.getTestResultId());
        vo.setUserId(dto.getUserId());
        vo.setCertRenewType(dto.getCertRenewType());
        vo.setCertReceiveType(dto.getCertReceiveType());
        vo.setCertNo(dto.getCertNo());
        vo.setCertName(dto.getCertName());
        vo.setCertPhone(dto.getCertPhone());
        vo.setCertAddress(dto.getCertAddress());
        return vo;
    }
}
