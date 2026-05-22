package com.app.springapp.service;

import com.app.springapp.domain.dto.TestApplyDTO;
import com.app.springapp.domain.dto.TestDTO;
import com.app.springapp.domain.vo.TestApplyVO;
import com.app.springapp.exception.UserException;
import com.app.springapp.repository.TestApplyDAO;
import com.app.springapp.repository.TestDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
@RequiredArgsConstructor
@Slf4j
public class TestApplyServiceImpl implements TestApplyService {

    private final TestApplyDAO testApplyDAO;
    private final TestDAO testDAO;

    // 원서 접수 (정원 초과 시 예외 발생)
    @Override
    public void apply(TestApplyDTO testApplyDTO) {
        // 시험 정보 조회
        TestDTO test = testDAO.findById(testApplyDTO.getTestId())
                .orElseThrow(() -> new UserException("존재하지 않는 시험입니다.", HttpStatus.NOT_FOUND));

        // 현재 신청 인원 조회
        int currentCount = testApplyDAO.countByTestId(testApplyDTO.getTestId());

        // 정원 초과 체크
        if (currentCount >= test.getTestLimit()) {
            throw new UserException("정원이 초과되어 신청할 수 없습니다.", HttpStatus.CONFLICT);
        }

        // 원서 접수 등록
        TestApplyVO testApplyVO = TestApplyVO.from(testApplyDTO);
        testApplyDAO.save(testApplyVO);
    }
}
