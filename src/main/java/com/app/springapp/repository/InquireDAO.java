package com.app.springapp.repository;

import com.app.springapp.domain.dto.InquireDTO;
import com.app.springapp.domain.vo.InquireVO;
import com.app.springapp.mapper.InquireMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InquireDAO {
    private final InquireMapper inquireMapper;

    public void save(InquireVO inquireVO) {
        inquireMapper.insert(inquireVO);
    }

    public List<InquireDTO> findAllInquires(InquireDTO inquireDTO) {
        return inquireMapper.selectAll(inquireDTO);
    }

    public int countInquires(InquireDTO inquireDTO) {
        return inquireMapper.countAll(inquireDTO);
    }

    public Optional<InquireDTO> findInquireById(Long id) {
        return Optional.ofNullable(inquireMapper.select(id));
    }

    public void update(InquireVO inquireVO) {
        inquireMapper.update(inquireVO);
    }

    public void delete(Long id) {
        inquireMapper.delete(id);
    }

    public List<InquireDTO> findAllInquiresForAdmin() {
        return inquireMapper.selectAllForAdmin();
    }
}