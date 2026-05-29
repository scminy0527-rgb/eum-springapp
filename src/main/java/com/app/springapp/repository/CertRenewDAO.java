package com.app.springapp.repository;

import com.app.springapp.domain.dto.CertRenewDTO;
import com.app.springapp.domain.vo.CertRenewVO;
import com.app.springapp.mapper.CertRenewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CertRenewDAO {
    private final CertRenewMapper certRenewMapper;

    public void save(CertRenewVO certRenewVO) {
        certRenewMapper.insert(certRenewVO);
    }

    public List<CertRenewDTO> findByUserId(Long userId) {
        return certRenewMapper.selectByUserId(userId);
    }

    public void cancel(Long id, Long userId) {
        certRenewMapper.cancelById(id, userId);
    }
}
