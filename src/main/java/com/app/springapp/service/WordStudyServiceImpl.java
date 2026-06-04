package com.app.springapp.service;

import com.app.springapp.domain.dto.response.WordStudyResponseDTO;
import com.app.springapp.domain.vo.WordStudyVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.WordStudyDAO;
import com.app.springapp.service.edu.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class WordStudyServiceImpl implements WordStudyService {
    private final WordStudyDAO wordStudyDAO;
    private final RewardService rewardService;

    // 단어 학습 기록 등록 -> 완료 처리
    @Override
    public void finishWordStudy(WordStudyVO wordStudyVO) {
        // 완료 처리 -> 학습 상태를 완료(1)로 설정
        wordStudyVO.setWordStudyIsCompleted(1);

        // 담아둠
        Long userId = wordStudyVO.getUserId();
        Long eduWordMapId = wordStudyVO.getEduWordMapId();

        // 이미 기록 있음 → update
        if (wordStudyDAO.findByUserIdAndEduWordMapId(userId, eduWordMapId).isPresent()) {
            wordStudyDAO.update(wordStudyVO);

            return;
        }

        // 처음 완료 → insert
        wordStudyDAO.save(wordStudyVO);

        //  새로운 학습 완료 경험치 지급
        rewardService.grantReward(userId, "LEARN", "WORD_DONE", eduWordMapId);
    }

    // 단어 학습 기록 조회
    @Override
    public WordStudyResponseDTO getWordStudy(Long userId, Long eduWordMapId) {
        return wordStudyDAO
                .findByUserIdAndEduWordMapId(userId, eduWordMapId)
                .orElseThrow(() -> new EduException("단어 학습 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    // 학습별 완료 단어 개수 조회
    @Override
    public int getCompletedWordCount(Long userId, Long eduId) {
        return wordStudyDAO.countCompletedWordsByEduId(userId, eduId);
    }

    // 학습별 전체 단어 개수 조회
    @Override
    public int getTotalWordCount(Long eduId) {
        return wordStudyDAO.countTotalWordsByEduId(eduId);
    }

    // 오늘 완료한 단어 개수 조회
    @Override
    public int getTodayCompletedWordCount(Long userId) {
        return wordStudyDAO.countTodayCompletedByUserId(userId);
    }
}
