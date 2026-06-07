package com.app.springapp.service;

import com.app.springapp.domain.dto.InquireDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InquireService {
    public void save(InquireDTO inquireDTO);
    public List<InquireDTO> findAllInquires(InquireDTO inquireDTO);
    public int countInquires(InquireDTO inquireDTO);
    public InquireDTO findInquireById(Long id);
    public void update(InquireDTO inquireDTO);
    public void delete(Long id);
    public List<InquireDTO> findAllInquiresForAdmin();
    public void updateContent(InquireDTO inquireDTO);
    public void saveWithFile(InquireDTO inquireDTO, List<MultipartFile> files) throws IOException;
}