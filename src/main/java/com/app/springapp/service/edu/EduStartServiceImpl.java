package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.EduStartCompleteResponseDTO;
import com.app.springapp.domain.dto.response.EduStartResponseDTO;
import com.app.springapp.domain.vo.EduStartVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.EduStartDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor =  {EduException.class})
public class EduStartServiceImpl implements EduStartService {
    private final EduStartDAO eduStartDAO;
    private final RewardService rewardService;

    // 학습 시작 기록 저장
    @Override
    public void startEdu(Long userId, Long eduId) {
        if (eduStartDAO.findByUserIdAndEduId(userId, eduId).isPresent()) {
            return;
        }

        EduStartVO eduStartVO = new EduStartVO();
        eduStartVO.setUserId(userId);
        eduStartVO.setEduId(eduId);
        eduStartVO.setEduStartTotalCount(5);
        eduStartVO.setEduStartCompletedCount(0);
        eduStartVO.setEduStartCorrectCount(0);

        eduStartDAO.save(eduStartVO);
    }

    // 학습 세션 완료 처리
    @Override
    public EduStartCompleteResponseDTO completeEduStart(Long userId, Long eduId, int eduStartTime) {
        if (!eduStartDAO.existsIncompleteEduStart(userId, eduId)) {
            startEdu(userId, eduId);
        }

        eduStartDAO.updateCompleted(userId, eduId, eduStartTime);

        EduStartResponseDTO completedEduStart = eduStartDAO.findLatestCompletedByUserIdAndEduId(userId, eduId);
        if (completedEduStart == null) {
            throw new EduException("완료된 학습 세션을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        int correctCount = completedEduStart.getEduStartCorrectCount();
        int totalCount = completedEduStart.getEduStartTotalCount();
        int accuracy = totalCount == 0 ? 0 : Math.round((float) correctCount / totalCount * 100);
        int exp = correctCount * 20;

        return EduStartCompleteResponseDTO
                .builder()
                .correctCount(correctCount)
                .totalCount(totalCount)
                .accuracy(accuracy)
                .spentTime(completedEduStart.getEduStartTime())
                .exp(exp)
                .build();
    }

    // 학습 세션 완료 여부 조회
    @Override
    public boolean isEduStartCompleted(Long userId, Long eduId) {
        return eduStartDAO.existsCompletedEduStart(userId, eduId);
    }

    // 학습 로드맵 이벤트 보상 수령
    @Override
    public int claimRoadmapReward(Long userId, Long eduId) {
        if (!eduStartDAO.existsCompletedEduStart(userId, eduId)) {
            throw new EduException("학습 완료 후 보상을 받을 수 있습니다.", HttpStatus.BAD_REQUEST);
        }

        return rewardService.grantReward(userId, "LEARN", "ROADMAP_REWARD", eduId);
    }

    // 학습 세션 문제 풀이 결과 반영
    @Override
    public void recordProgress(Long userId, Long eduId, int isCorrect) {
        EduStartResponseDTO eduStart = eduStartDAO.findByUserIdAndEduId(userId, eduId)
                .orElseThrow(() -> new EduException("진행 중인 학습 세션을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        eduStartDAO.updateProgress(eduStart.getId(), isCorrect);
    }

}
