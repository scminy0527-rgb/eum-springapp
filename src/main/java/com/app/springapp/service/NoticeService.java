package com.app.springapp.service;

import com.app.springapp.domain.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    // 공지사항 등록
    void save(NoticeDTO noticeDTO);

    // 공지사항 목록 조회
    List<NoticeDTO> findAllNotices(NoticeDTO noticeDTO);

    // 전체 개수 조회
    int countNotices(NoticeDTO noticeDTO);

    // 공지사항 상세 조회
    NoticeDTO findNoticeById(Long id);

    // 공지사항 수정
    void update(NoticeDTO noticeDTO);

    // 공지사항 삭제
    void delete(Long id);
}