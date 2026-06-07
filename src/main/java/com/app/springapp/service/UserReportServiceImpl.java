package com.app.springapp.service;

import com.app.springapp.domain.dto.request.UserReportRequestDTO;
import com.app.springapp.domain.vo.UserReportVO;
import com.app.springapp.repository.UserReportDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserReportServiceImpl implements UserReportService {
    private final UserReportDAO userReportDAO;
    @Override
    public void reportUser(Long userId, UserReportRequestDTO userReportRequestDTO) {
        UserReportVO userReportVO = UserReportVO.from(userReportRequestDTO);
        userReportVO.setUserId(userId);
        userReportDAO.save(userReportVO);
    }
}
