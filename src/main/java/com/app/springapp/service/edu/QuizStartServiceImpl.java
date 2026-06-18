package com.app.springapp.service.edu;

import com.app.springapp.domain.vo.QuizStartVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.QuizDAO;
import com.app.springapp.repository.QuizStartDAO;
import com.app.springapp.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class QuizStartServiceImpl implements QuizStartService {
    private final QuizStartDAO quizStartDAO;

    private final UserDAO userDAO;
    private final QuizDAO quizDAO;

    // 퀴즈 시작 기록 등록
    @Override
    public void startQuiz(Long userId, Long quizId) {
        userDAO.findUserById(userId)
                .orElseThrow(() -> new EduException("유저 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        quizDAO.findById(quizId)
                .orElseThrow(() -> new EduException("퀴즈 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if (quizStartDAO.findByUserIdAndQuizId(userId, quizId).isPresent()) {
            quizStartDAO.updateStartAt(userId, quizId);
            return;
        }

        QuizStartVO quizStartVO = new QuizStartVO();
        quizStartVO.setUserId(userId);
        quizStartVO.setQuizId(quizId);

        quizStartDAO.save(quizStartVO);
    }

    // 퀴즈 진행 문제 수 증가
    @Override
    public void updateProgress(Long userId, Long quizId, int totalCount, int isCorrect) {
        quizStartDAO.findByUserIdAndQuizId(userId, quizId)
                .orElseThrow(() -> new EduException("퀴즈 시작 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        quizStartDAO.updateProgress(userId, quizId, totalCount, isCorrect);
    }


}
