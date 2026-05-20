package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.QuizResponseDTO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.QuizDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class QuizServiceImpl implements QuizService {
    private final QuizDAO quizDAO;

    // 퀴즈 목록 조회
    @Override
    public List<QuizResponseDTO> getQuizList() {
        return quizDAO.findQuizList();
    }

    // 퀴즈 상세 조회
    @Override
    public QuizResponseDTO getQuizById(Long id) {
        return quizDAO.findById(id)
                .orElseThrow(() -> new EduException("퀴즈 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}
