package com.app.springapp.service;

import com.app.springapp.domain.dto.request.PostReportRequestDTO;

public interface PostReportService {
    public void reportPost(Long userId, PostReportRequestDTO postReportRequestDTO);
}
