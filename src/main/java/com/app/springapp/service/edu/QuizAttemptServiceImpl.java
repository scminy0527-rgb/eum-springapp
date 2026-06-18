package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.request.QuizSubmitAnswerRequestDTO;
import com.app.springapp.domain.dto.request.QuizSubmitRequestDTO;
import com.app.springapp.domain.dto.response.QuizAttemptDetailResponseDTO;
import com.app.springapp.domain.dto.response.QuizAttemptResponseDTO;
import com.app.springapp.domain.dto.response.QuizChoiceResponseDTO;
import com.app.springapp.domain.vo.QuizAttemptDetailVO;
import com.app.springapp.domain.vo.QuizAttemptVO;
import com.app.springapp.domain.vo.QuizStartVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.QuizAttemptDAO;
import com.app.springapp.repository.QuizAttemptDetailDAO;
import com.app.springapp.repository.QuizChoiceDAO;
import com.app.springapp.repository.QuizStartDAO;
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
public class QuizAttemptServiceImpl implements QuizAttemptService {
    private final QuizAttemptDAO quizAttemptDAO;
    private final QuizAttemptDetailDAO quizAttemptDetailDAO;
    private final QuizChoiceDAO quizChoiceDAO;
    private final QuizStartDAO quizStartDAO;
    private final RewardService rewardService;

    // 퀴즈 제출 및 채점
    @Override
    public QuizAttemptResponseDTO submitQuiz(Long quizId, QuizSubmitRequestDTO requestDTO) {
        QuizStartVO quizStartVO = quizStartDAO
                .findByUserIdAndQuizId(requestDTO.getUserId(), quizId)
                .orElse(null);

        int submittedAnswerCount = requestDTO.getAnswers().size();

        int totalCount = quizStartVO != null && quizStartVO.getQuizStartCompletedCount() > 0
                ? quizStartVO.getQuizStartCompletedCount()
                : submittedAnswerCount;

        int score = quizStartVO != null && quizStartVO.getQuizStartCompletedCount() > 0
                ? quizStartVO.getQuizStartCorrectCount()
                : 0;

        QuizAttemptVO quizAttemptVO = new QuizAttemptVO();
        quizAttemptVO.setUserId(requestDTO.getUserId());
        quizAttemptVO.setQuizId(quizId);
        quizAttemptVO.setQuizAttemptScore(0);
        quizAttemptVO.setQuizAttemptTotalCount(totalCount);
        quizAttemptVO.setQuizAttemptTime(requestDTO.getQuizAttemptTime());

        quizAttemptDAO.save(quizAttemptVO);

        for (QuizSubmitAnswerRequestDTO answer : requestDTO.getAnswers()) {
            QuizChoiceResponseDTO correctChoice = quizChoiceDAO.findCorrectChoiceByQuestionId(answer.getQuizQuestionId())
                    .orElseThrow(() -> new EduException("정답 보기를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

            int isCorrect = correctChoice.getId().equals(answer.getQuizChoiceId()) ? 1 : 0;

            if (quizStartVO == null || quizStartVO.getQuizStartCompletedCount() == 0) {
                if (isCorrect == 1) {
                    score++;
                }
            }

            QuizAttemptDetailVO detailVO = new QuizAttemptDetailVO();
            detailVO.setQuizAttemptId(quizAttemptVO.getId());
            detailVO.setQuizQuestionId(answer.getQuizQuestionId());
            detailVO.setQuizChoiceId(answer.getQuizChoiceId());
            detailVO.setQuizAttemptDetailIsCorrect(isCorrect);

            quizAttemptDetailDAO.save(detailVO);
        }

        quizAttemptVO.setQuizAttemptScore(score);
        quizAttemptDAO.updateScore(quizAttemptVO);

        // 퀴즈 완료 보상 지급
        if (score >= Math.ceil(totalCount / 2.0)) {
            rewardService.grantReward(
                    requestDTO.getUserId(),
                    "QUIZ",
                "CHAPTER_DONE",
                    quizAttemptVO.getId()
            );
        }

        return quizAttemptDAO.findById(quizAttemptVO.getId())
                .orElseThrow(() -> new EduException("퀴즈 응시 결과를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 퀴즈 응시 결과 조회
    @Override
    @Transactional(readOnly = true)
    public QuizAttemptResponseDTO getAttemptById(Long id) {
        return quizAttemptDAO.findById(id)
                .orElseThrow(() -> new EduException("퀴즈 응시 결과를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 사용자별 퀴즈 응시 기록 조회
    @Override
    @Transactional(readOnly = true)
    public List<QuizAttemptResponseDTO> getAttemptsByUserId(Long userId) {
        return quizAttemptDAO.findAttemptsByUserId(userId);
    }

    // 퀴즈 응시별 문제 결과 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<QuizAttemptDetailResponseDTO> getAttemptDetailsByAttemptId(Long quizAttemptId) {
        return quizAttemptDetailDAO.findDetailsByAttemptId(quizAttemptId);
    }
}
