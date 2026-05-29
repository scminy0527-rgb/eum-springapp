package com.app.springapp.mapper;

import com.app.springapp.domain.dto.CertRenewDTO;
import com.app.springapp.domain.vo.CertRenewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CertRenewMapper {
    void insert(CertRenewVO certRenewVO);
    List<CertRenewDTO> selectByUserId(@Param("userId") Long userId);
    void cancelById(@Param("id") Long id, @Param("userId") Long userId);
}
