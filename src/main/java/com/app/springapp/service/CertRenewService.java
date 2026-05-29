package com.app.springapp.service;

import com.app.springapp.domain.dto.CertRenewDTO;

import java.util.List;

public interface CertRenewService {
    void apply(CertRenewDTO certRenewDTO);
    List<CertRenewDTO> getMyApplications(Long userId);
    void cancel(Long id, Long userId);
}
