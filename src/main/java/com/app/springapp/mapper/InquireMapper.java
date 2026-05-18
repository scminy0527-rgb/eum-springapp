package com.app.springapp.mapper;

import com.app.springapp.domain.dto.InquireDTO;
import com.app.springapp.domain.vo.InquireVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface InquireMapper {
    public void insert(InquireVO inquireVO);
    public List<InquireDTO> selectAll(InquireDTO inquireDTO);
    public int countAll(InquireDTO inquireDTO);
    public InquireDTO select(Long id);
    public void update(InquireVO inquireVO);
    public void delete(Long id);
    public List<InquireDTO> selectAllForAdmin();
}