package com.app.springapp.mapper;

import com.app.springapp.domain.vo.CertRenewVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CertRenewMapper {
    void insert(CertRenewVO certRenewVO);
}
