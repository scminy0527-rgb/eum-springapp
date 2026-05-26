package com.app.springapp.service;

import com.app.springapp.domain.dto.CertRenewDTO;
import com.app.springapp.domain.vo.CertRenewVO;
import com.app.springapp.repository.CertRenewDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class CertRenewServiceImpl implements CertRenewService {

    private final CertRenewDAO certRenewDAO;

    @Override
    public void apply(CertRenewDTO certRenewDTO) {
        certRenewDAO.save(CertRenewVO.from(certRenewDTO));
    }
}
