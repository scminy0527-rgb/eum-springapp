package com.app.springapp.service;

import com.app.springapp.domain.dto.request.PostReportRequestDTO;
import com.app.springapp.domain.vo.PostReportVO;
import com.app.springapp.repository.PostReportDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PostReportServiceImpl implements PostReportService {
    private final PostReportDAO postReportDAO;

    @Override
    public void reportPost(Long userId, PostReportRequestDTO postReportRequestDTO) {
        PostReportVO postReportVO = PostReportVO.from(postReportRequestDTO);
        postReportVO.setUserId(userId);
        postReportDAO.save(postReportVO);
    }
}
