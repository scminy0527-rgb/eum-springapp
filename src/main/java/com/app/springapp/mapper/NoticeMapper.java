package com.app.springapp.mapper;

import com.app.springapp.domain.dto.NoticeDTO;
import com.app.springapp.domain.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    public void insert(NoticeVO noticeVO);
    public List<NoticeDTO> selectAll(NoticeDTO noticeDTO);
    public int countAll(NoticeDTO noticeDTO);
    public NoticeDTO select(Long id);
    public void update(NoticeVO noticeVO);
    public void delete(Long id);
}