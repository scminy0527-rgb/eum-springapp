package com.app.springapp.repository;

import com.app.springapp.domain.vo.CertRenewVO;
import com.app.springapp.mapper.CertRenewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CertRenewDAO {
    private final CertRenewMapper certRenewMapper;

    public void save(CertRenewVO certRenewVO) {
        certRenewMapper.insert(certRenewVO);
    }
}
