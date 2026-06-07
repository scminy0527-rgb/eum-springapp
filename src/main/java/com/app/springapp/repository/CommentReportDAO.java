package com.app.springapp.repository;

import com.app.springapp.domain.vo.CommentReportVO;
import com.app.springapp.mapper.CommentReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentReportDAO {
    private final CommentReportMapper commentReportMapper;

    public void save(CommentReportVO commentReportVO) {
        commentReportMapper.insert(commentReportVO);
    }
}
