package com.app.springapp.service;

import com.app.springapp.domain.dto.response.MyPageProfileResponseDTO;
import com.app.springapp.domain.vo.UserExpHistoryVO;
import com.app.springapp.repository.UserExpDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class UserExpServiceImpl implements UserExpService {
    private final UserExpDAO userExpDAO;

    private static final String TYPE_ATTENDANCE = "ATTENDANCE";
    private static final String TYPE_POST = "POST";
    private static final String TYPE_COMMENT = "COMMENT";
    private static final String TYPE_STUDY = "STUDY";

    private static final Long ATTENDANCE_EXP = 40L;
    private static final Long COMMUNITY_EXP = 5L;
    private static final Long STUDY_EXP = 20L;

    private static final int COMMUNITY_DAILY_LIMIT = 3;
    private static final int MAX_LEVEL = 100;

    //    게시글 작성 경험치 지급
    @Override
    public void addPostExp(Long userId, Long postId) {
        if (canAddCommunityExp(userId)) {
            addExp(userId, TYPE_POST, COMMUNITY_EXP, postId);
        }
    }

    //    댓글 작성 경험치 지급
    @Override
    public void addCommentExp(Long userId, Long commentId) {
        if (canAddCommunityExp(userId)) {
            addExp(userId, TYPE_COMMENT, COMMUNITY_EXP, commentId);
        }
    }

    //    학습 완료 경험치 지급
    @Override
    public void addStudyExp(Long userId, Long eduId) {
        if (eduId == null) {
            return;
        }

        int expHistoryCount = userExpDAO.findTargetExpHistoryCount(userId, TYPE_STUDY, eduId);

        if (expHistoryCount == 0) {
            addExp(userId, TYPE_STUDY, STUDY_EXP, eduId);
        }
    }

    //    출석 완료 레벨 경험치 지급
    @Override
    public void addAttendanceExp(Long userId) {
        int todayAttendanceExpCount = userExpDAO.findTodayAttendanceExpCount(userId);

        if (todayAttendanceExpCount == 0) {
            addExp(userId, TYPE_ATTENDANCE, ATTENDANCE_EXP, null);
        }
    }

    //    마이페이지 프로필 레벨 정보 계산
    @Override
    public void setLevelInfo(MyPageProfileResponseDTO profileResponseDTO) {
        Long totalExp = profileResponseDTO.getUserExp();

        if (totalExp == null || totalExp < 0) {
            totalExp = 0L;
        }

        LevelInfo levelInfo = calculateLevelInfo(totalExp);

        profileResponseDTO.setUserLevel(levelInfo.level);
        profileResponseDTO.setUserLevelName(levelInfo.levelName);
        profileResponseDTO.setCurrentLevelExp(levelInfo.currentLevelExp);
        profileResponseDTO.setNextLevelExp(levelInfo.nextLevelExp);
        profileResponseDTO.setRemainingExp(levelInfo.remainingExp);
        profileResponseDTO.setExpPercent(levelInfo.expPercent);
    }

    //    회원탈퇴 시 경험치 이력 삭제
    @Override
    public void deleteUserExpHistoryByUserId(Long userId) {
        userExpDAO.deleteUserExpHistoryByUserId(userId);
    }

    //    게시글/댓글 경험치 지급 가능 여부 확인
    private boolean canAddCommunityExp(Long userId) {
        return userExpDAO.findTodayCommunityExpCount(userId) < COMMUNITY_DAILY_LIMIT;
    }

    //    경험치 증가와 지급 이력을 함께 저장
    private void addExp(Long userId, String type, Long amount, Long targetId) {
        userExpDAO.updateUserExp(userId, amount);

        UserExpHistoryVO userExpHistoryVO = new UserExpHistoryVO();
        userExpHistoryVO.setUserId(userId);
        userExpHistoryVO.setUserExpHistoryType(type);
        userExpHistoryVO.setUserExpHistoryAmount(amount);
        userExpHistoryVO.setUserExpHistoryTargetId(targetId);

        userExpDAO.saveUserExpHistory(userExpHistoryVO);
    }

    //    총 경험치로 현재 레벨 정보를 계산
    private LevelInfo calculateLevelInfo(Long totalExp) {
        long maxLevelTotalExp = getMaxLevelTotalExp();

        if (totalExp >= maxLevelTotalExp) {
            LevelInfo maxLevelInfo = new LevelInfo();
            maxLevelInfo.level = MAX_LEVEL;
            maxLevelInfo.levelName = getLevelName(MAX_LEVEL);
            maxLevelInfo.currentLevelExp = maxLevelTotalExp;
            maxLevelInfo.nextLevelExp = maxLevelTotalExp;
            maxLevelInfo.remainingExp = 0L;
            maxLevelInfo.expPercent = 100;

            return maxLevelInfo;
        }

        int level = 1;
        long remainingTotalExp = totalExp;

        while (level < MAX_LEVEL) {
            long requiredExp = getRequiredExp(level);

            if (remainingTotalExp < requiredExp) {
                break;
            }

            remainingTotalExp -= requiredExp;
            level++;
        }

        long nextLevelExp = getRequiredExp(level);
        long currentLevelExp = remainingTotalExp;
        long remainingExp = nextLevelExp - currentLevelExp;
        int expPercent = (int) Math.floor((double) currentLevelExp / nextLevelExp * 100);

        LevelInfo levelInfo = new LevelInfo();
        levelInfo.level = level;
        levelInfo.levelName = getLevelName(level);
        levelInfo.currentLevelExp = currentLevelExp;
        levelInfo.nextLevelExp = nextLevelExp;
        levelInfo.remainingExp = remainingExp;
        levelInfo.expPercent = expPercent;

        return levelInfo;
    }

    //    현재 레벨에서 다음 레벨까지 필요한 경험치 계산
    private long getRequiredExp(int level) {
        return 100L + ((long) level - 1L) * 20L;
    }

    //    Lv.100 도달에 필요한 총 누적 경험치 계산
    private long getMaxLevelTotalExp() {
        long totalRequiredExp = 0L;

        for (int level = 1; level < MAX_LEVEL; level++) {
            totalRequiredExp += getRequiredExp(level);
        }

        return totalRequiredExp;
    }

    //    레벨 구간별 이름 조회
    private String getLevelName(int level) {
        if (level >= 100) {
            return "이음";
        }

        if (level >= 90) {
            return "수어 마스터";
        }

        if (level >= 80) {
            return "연결자";
        }

        if (level >= 70) {
            return "숙련가";
        }

        if (level >= 60) {
            return "공감가";
        }

        if (level >= 50) {
            return "표현가";
        }

        if (level >= 40) {
            return "소통가";
        }

        if (level >= 30) {
            return "실천가";
        }

        if (level >= 20) {
            return "학습자";
        }

        if (level >= 10) {
            return "새싹 수어인";
        }

        return "입문자";
    }

    //    레벨 계산 결과를 서비스 내부에서 임시로 담는 클래스
    private static class LevelInfo {
        //  현재 레벨
        private int level;

        //  현재 레벨 이름
        private String levelName;

        //  현재 레벨에서 획득한 경험치
        private long currentLevelExp;

        //  다음 레벨까지 필요한 경험치
        private long nextLevelExp;

        //  다음 레벨까지 남은 경험치
        private long remainingExp;

        //  현재 레벨 경험치 진행률
        private int expPercent;
    }
}
