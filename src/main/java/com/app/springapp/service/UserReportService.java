package com.app.springapp.service;

import com.app.springapp.domain.dto.request.UserReportRequestDTO;

public interface UserReportService {
//    커뮤니티 유저 신고
    public void reportUser(Long userId, UserReportRequestDTO userReportRequestDTO);
}
