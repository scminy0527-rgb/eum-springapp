package com.app.springapp.repository;

import com.app.springapp.domain.vo.PostReportVO;
import com.app.springapp.mapper.PostReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostReportDAO {
    private final PostReportMapper postReportMapper;

    public void save(PostReportVO postReportVO) {
        postReportMapper.insert(postReportVO);
    }
}
