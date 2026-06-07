package com.app.springapp.mapper;

import com.app.springapp.domain.vo.CommentReportVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentReportMapper {
    public void insert(CommentReportVO commentReportVO);
}
