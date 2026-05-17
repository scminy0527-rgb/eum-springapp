package com.app.springapp.service;

import com.app.springapp.domain.dto.NoticeDTO;
import com.app.springapp.domain.vo.NoticeVO;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.NoticeDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDAO noticeDAO;

    @Override
    public void save(NoticeDTO noticeDTO) {
        NoticeVO noticeVO = NoticeVO.from(noticeDTO);
        noticeDAO.save(noticeVO);
    }

    @Override
    public List<NoticeDTO> findAllNotices(NoticeDTO noticeDTO) {
        return noticeDAO.findAllNotices(noticeDTO);
    }

    @Override
    public int countNotices(NoticeDTO noticeDTO) {
        return noticeDAO.countNotices(noticeDTO);
    }

    @Override
    public NoticeDTO findNoticeById(Long id) {
        return noticeDAO.findNoticeById(id)
                .orElseThrow(() -> new UserException("공지사항을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    @Override
    public void update(NoticeDTO noticeDTO) {
        NoticeVO noticeVO = NoticeVO.from(noticeDTO);
        noticeDAO.update(noticeVO);
    }

    @Override
    public void delete(Long id) {
        noticeDAO.delete(id);
    }
}