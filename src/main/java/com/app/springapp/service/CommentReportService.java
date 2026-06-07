package com.app.springapp.service;

import com.app.springapp.domain.dto.request.CommentReportRequestDTO;

public interface CommentReportService {
    public void reportComment(Long userId, CommentReportRequestDTO commentReportRequestDTO);
}
