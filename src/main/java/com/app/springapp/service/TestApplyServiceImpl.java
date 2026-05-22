package com.app.springapp.service;

import com.app.springapp.domain.dto.TestApplyDTO;
import com.app.springapp.domain.dto.TestDTO;
import com.app.springapp.domain.vo.TestApplyVO;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.TestApplyDAO;
import com.app.springapp.repository.TestDAO;
import com.app.springapp.util.LocalFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
@Slf4j
public class TestApplyServiceImpl implements TestApplyService {

    private final TestApplyDAO testApplyDAO;
    private final TestDAO testDAO;
    private final LocalFileUtil localFileUtil;

    // 접수 취소
    @Override
    public void cancel(Long id, Long userId) {
        testApplyDAO.deleteById(id, userId);
    }

    // 내 접수 목록 조회
    @Override
    public List<TestApplyDTO> getMyApplyList(Long userId) {
        return testApplyDAO.findByUserId(userId);
    }

    // 원서 접수 (정원 초과 시 예외 발생)
    @Override
    public void apply(TestApplyDTO testApplyDTO, List<MultipartFile> files) {
        // 시험 정보 조회
        TestDTO test = testDAO.findById(testApplyDTO.getTestId())
                .orElseThrow(() -> new UserException("존재하지 않는 시험입니다.", HttpStatus.NOT_FOUND));

        // 현재 신청 인원 조회
        int currentCount = testApplyDAO.countByTestId(testApplyDTO.getTestId());

        // 정원 초과 체크
        if (currentCount >= test.getTestLimit()) {
            throw new UserException("정원이 초과되어 신청할 수 없습니다.", HttpStatus.CONFLICT);
        }

        // 파일 저장
        if (files != null && !files.isEmpty()) {
            try {
                List<String> savedNames = localFileUtil.saveFiles(files);
                testApplyDTO.setFilePaths(String.join(",", savedNames));
            } catch (IOException e) {
                throw new UserException("파일 저장에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // 원서 접수 등록
        TestApplyVO testApplyVO = TestApplyVO.from(testApplyDTO);
        testApplyDAO.save(testApplyVO);
    }
}
