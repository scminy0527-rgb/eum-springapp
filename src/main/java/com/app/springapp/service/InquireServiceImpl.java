package com.app.springapp.service;

import com.app.springapp.domain.dto.InquireDTO;
import com.app.springapp.domain.vo.InquireVO;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.InquireDAO;
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
public class InquireServiceImpl implements InquireService {

    private final InquireDAO inquireDAO;

    @Override
    public void save(InquireDTO inquireDTO) {
        InquireVO inquireVO = InquireVO.from(inquireDTO);
        inquireDAO.save(inquireVO);
    }

    @Override
    public List<InquireDTO> findAllInquires(InquireDTO inquireDTO) {
        return inquireDAO.findAllInquires(inquireDTO);
    }

    @Override
    public int countInquires(InquireDTO inquireDTO) {
        return inquireDAO.countInquires(inquireDTO);
    }

    @Override
    public InquireDTO findInquireById(Long id) {
        return inquireDAO.findInquireById(id)
                .orElseThrow(() -> new UserException("문의를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    @Override
    public void update(InquireDTO inquireDTO) {
        InquireVO inquireVO = InquireVO.from(inquireDTO);
        inquireDAO.update(inquireVO);
    }

    @Override
    public void delete(Long id) {
        inquireDAO.delete(id);
    }

    @Override
    public List<InquireDTO> findAllInquiresForAdmin() {
        return inquireDAO.findAllInquiresForAdmin();
    }

    @Override
    public void updateContent(InquireDTO inquireDTO) {
        inquireDAO.updateContent(inquireDTO);
    }


}