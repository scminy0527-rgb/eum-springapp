package com.app.springapp.mapper;

import com.app.springapp.domain.vo.UserReportVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserReportMapper {
//    커뮤니티 유저 신고
    public void insert(UserReportVO userReportVO);
}
