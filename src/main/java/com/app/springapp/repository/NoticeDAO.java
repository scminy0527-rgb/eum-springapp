package com.app.springapp.repository;

import com.app.springapp.domain.dto.NoticeDTO;
import com.app.springapp.domain.vo.NoticeVO;
import com.app.springapp.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoticeDAO {
    private final NoticeMapper noticeMapper;

    // 공지사항 등록
    public void save(NoticeVO noticeVO) {
        noticeMapper.insert(noticeVO);
    }

    // 공지사항 목록 조회 (카테고리 필터 + 페이징)
    public List<NoticeDTO> findAllNotices(NoticeDTO noticeDTO) {
        return noticeMapper.selectAll(noticeDTO);
    }

    // 전체 개수 조회 (페이징용)
    public int countNotices(NoticeDTO noticeDTO) {
        return noticeMapper.countAll(noticeDTO);
    }

    // 공지사항 상세 조회
    public Optional<NoticeDTO> findNoticeById(Long id) {
        return Optional.ofNullable(noticeMapper.select(id));
    }

    // 공지사항 수정
    public void update(NoticeVO noticeVO) {
        noticeMapper.update(noticeVO);
    }

    // 공지사항 삭제
    public void delete(Long id) {
        noticeMapper.delete(id);
    }
}