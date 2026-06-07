package com.app.springapp.service;

import com.app.springapp.domain.dto.InquireDTO;
import com.app.springapp.domain.vo.InquireVO;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.InquireDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Value("${file.upload.inquire}")
    private String uploadPath;

    @Override
    public void saveWithFile(InquireDTO inquireDTO, List<MultipartFile> files) throws IOException {
        log.info("files: {}", files);
        if (files != null && !files.isEmpty()) {
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            List<String> urls = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadPath + File.separator + savedName));
                    urls.add("/uploads/inquire/" + savedName);
                }
            }
            // 쉼표로 구분해서 저장
            inquireDTO.setInquireFileUrl(String.join(",", urls));
            log.info("저장할 fileUrl: {}", inquireDTO.getInquireFileUrl());
        }
        inquireDAO.save(InquireVO.from(inquireDTO));
    }

}