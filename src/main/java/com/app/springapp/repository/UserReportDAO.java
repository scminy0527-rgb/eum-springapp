package com.app.springapp.repository;

import com.app.springapp.domain.vo.UserReportVO;
import com.app.springapp.mapper.UserReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserReportDAO {
    private final UserReportMapper userReportMapper;

//    유저 신고
    public void save(UserReportVO userReportVO) {
        userReportMapper.insert(userReportVO);
    }
}
