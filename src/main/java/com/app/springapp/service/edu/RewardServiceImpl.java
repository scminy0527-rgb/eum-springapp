package com.app.springapp.service.edu;

import com.app.springapp.domain.vo.RewardPolicyVO;
import com.app.springapp.domain.vo.UserBadgeVO;
import com.app.springapp.domain.vo.UserRewardHistoryVO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.RewardDAO;
import com.app.springapp.repository.RewardPolicyDAO;
import com.app.springapp.repository.UserRewardHistoryDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor =  {EduException.class})
public class RewardServiceImpl implements RewardService {
    private final RewardPolicyDAO rewardPolicyDAO;
    private final UserRewardHistoryDAO userRewardHistoryDAO;
    private final RewardDAO rewardDAO;

    // 조건에 맞는 학습 보상 이력과 배지를 자동 지급
    @Override
    public int grantReward(Long userId, String rewardType, String rewardCondition, Long rewardReferenceId) {
        RewardPolicyVO policy = rewardPolicyDAO.findByTypeAndCondition(rewardType, rewardCondition);

        if (policy == null) {
            throw new IllegalStateException("보상 정책을 찾을 수 없습니다.");
        }

        if (userRewardHistoryDAO.existsRewardHistory(userId, policy.getId(), rewardReferenceId)) {
            return 0;
        }

        UserRewardHistoryVO history = new UserRewardHistoryVO();
        history.setUserId(userId);
        history.setRewardPolicyId(policy.getId());
        history.setRewardReferenceId(rewardReferenceId);
        history.setUserRewardExp(policy.getRewardExp());
        userRewardHistoryDAO.save(history);

        if (policy.getBadgeId() != null && !rewardDAO.existsUserBadge(userId, policy.getBadgeId())) {
            UserBadgeVO userBadge = new UserBadgeVO();
            userBadge.setUserId(userId);
            userBadge.setBadgeId(policy.getBadgeId());
            rewardDAO.saveUserBadge(userBadge);
        }

        return policy.getRewardExp();
    }
}
