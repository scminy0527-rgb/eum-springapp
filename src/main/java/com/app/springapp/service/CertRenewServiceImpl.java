package com.app.springapp.service;

import com.app.springapp.domain.dto.CertRenewDTO;
import com.app.springapp.domain.vo.CertRenewVO;
import com.app.springapp.repository.CertRenewDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
public class CertRenewServiceImpl implements CertRenewService {

    private final CertRenewDAO certRenewDAO;

    @Override
    public void apply(CertRenewDTO certRenewDTO) {
        certRenewDAO.save(CertRenewVO.from(certRenewDTO));
    }

    @Override
    public List<CertRenewDTO> getMyApplications(Long userId) {
        return certRenewDAO.findByUserId(userId);
    }

    @Override
    public void cancel(Long id, Long userId) {
        certRenewDAO.cancel(id, userId);
    }
}
