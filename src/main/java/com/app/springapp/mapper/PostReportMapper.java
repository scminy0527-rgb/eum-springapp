package com.app.springapp.mapper;

import com.app.springapp.domain.vo.PostReportVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostReportMapper {
    public void insert(PostReportVO postReportVO);
}
