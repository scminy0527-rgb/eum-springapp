package com.app.springapp.service;

import com.app.springapp.domain.dto.request.CommentReportRequestDTO;
import com.app.springapp.domain.vo.CommentReportVO;
import com.app.springapp.repository.CommentReportDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CommentReportServiceImpl implements CommentReportService {
    private final CommentReportDAO commentReportDAO;

    @Override
    public void reportComment(Long userId, CommentReportRequestDTO commentReportRequestDTO) {
        CommentReportVO commentReportVO = CommentReportVO.from(commentReportRequestDTO);
        commentReportVO.setUserId(userId);
        commentReportDAO.save(commentReportVO);
    }
}
