package com.app.springapp.repository;

import com.app.springapp.domain.vo.UserRewardHistoryVO;
import com.app.springapp.mapper.UserRewardHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRewardHistoryDAO {
    private final UserRewardHistoryMapper userRewardHistoryMapper;

    // 동일한 보상 지급 여부 조회
    public boolean existsRewardHistory(Long userId, Long rewardPolicyId, Long rewardReferenceId) {
        return userRewardHistoryMapper
                .countByUserIdAndPolicyIdAndReferenceId(userId, rewardPolicyId, rewardReferenceId) > 0;
    }

    // 사용자 보상 지급 이력 등록
    public void save(UserRewardHistoryVO userRewardHistoryVO) {
        userRewardHistoryMapper.insert(userRewardHistoryVO);
    }

    // 사용자별 보상 지급 이력 조회
    public List<UserRewardHistoryVO> findByUserId(Long userId) {
        return userRewardHistoryMapper.selectByUserId(userId);
    }

    // 사용자가 획득한 누적 보상 EXP 조회
    public int findTotalRewardExp(Long userId) {
        return userRewardHistoryMapper.sumRewardExpByUserId(userId);
    }

    // 기준 기록별 지급 EXP 합계 조회
    public int findRewardExpByReferenceId(Long userId, Long rewardReferenceId) {
        return userRewardHistoryMapper.sumRewardExpByReferenceId(userId, rewardReferenceId);
    }

    // 회원 탈퇴 시 사용자 보상 지급 이력 삭제
    public void deleteByUserId(Long userId) {
        userRewardHistoryMapper.deleteByUserId(userId);
    }

    // 학습 보상 EXP 조회
    public int findLearnRewardExpByUserId(Long userId) {
        return userRewardHistoryMapper.sumLearnRewardExpByUserId(userId);
    }
}