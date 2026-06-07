package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.QuizQuestionResponseDTO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.QuizChoiceDAO;
import com.app.springapp.repository.QuizQuestionDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class QuizQuestionServiceImpl implements QuizQuestionService {
    private final QuizQuestionDAO quizQuestionDAO;
    private final QuizChoiceDAO quizChoiceDAO;

    // 문제별 보기 목록을 포함해서 퀴즈 문제를 조회
    @Override
    public List<QuizQuestionResponseDTO> getQuestionsByQuizId(Long quizId) {
        List<QuizQuestionResponseDTO> questions = quizQuestionDAO.findQuestionsByQuizId(quizId);

        questions.forEach(question ->
                question.setChoices(quizChoiceDAO.findChoicesByQuestionId(question.getId()))
        );

        return questions;
    }

    // 문제 상세 조회
    @Override
    public QuizQuestionResponseDTO getQuestionById(Long id) {
        return quizQuestionDAO.findById(id)
                .orElseThrow(() -> new EduException("퀴즈 문제 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}
