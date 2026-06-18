package com.app.springapp.mapper;

import com.app.springapp.domain.vo.UserRewardHistoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRewardHistoryMapper {

    // 동일한 보상 지급 여부 조회
    public int countByUserIdAndPolicyIdAndReferenceId(Long userId, Long rewardPolicyId, Long rewardReferenceId);

    // 사용자 보상 지급 이력 등록
    public void insert(UserRewardHistoryVO userRewardHistoryVO);

    // 사용자별 보상 지급 이력 조회
    public List<UserRewardHistoryVO> selectByUserId(Long userId);

    // 사용자가 획득한 누적 보상 EXP 조회
    public int sumRewardExpByUserId(Long userId);

    // 기준 기록별 지급 EXP 합계 조회
    public int sumRewardExpByReferenceId(Long userId, Long rewardReferenceId);

    // 회원 탈퇴 시 사용자 보상 지급 이력 삭제
    public void deleteByUserId(Long userId);

    // 학습 보상 EXP 조회
    public int sumLearnRewardExpByUserId(Long userId);

}
