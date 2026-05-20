package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.QuizChoiceResponseDTO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.QuizChoiceDAO;
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
public class QuizChoiceServiceImpl implements QuizChoiceService {
    private final QuizChoiceDAO quizChoiceDAO;

    // 문제별 보기 목록 조회
    @Override
    public List<QuizChoiceResponseDTO> getChoicesByQuestionId(Long quizQuestionId) {
        return quizChoiceDAO.findChoicesByQuestionId(quizQuestionId);
    }

    // 정답 보기 조회
    @Override
    public QuizChoiceResponseDTO getCorrectChoiceByQuestionId(Long quizQuestionId) {
        return quizChoiceDAO.findCorrectChoiceByQuestionId(quizQuestionId)
                .orElseThrow(() -> new EduException("정답 보기를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 보기 상세 조회
    @Override
    public QuizChoiceResponseDTO getChoiceById(Long id) {
        return quizChoiceDAO.findById(id)
                .orElseThrow(() -> new EduException("퀴즈 보기를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}
