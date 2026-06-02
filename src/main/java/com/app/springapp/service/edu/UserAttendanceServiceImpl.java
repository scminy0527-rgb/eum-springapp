package com.app.springapp.service.edu;

import com.app.springapp.domain.dto.response.UserAttendanceSummaryResponseDTO;
import com.app.springapp.exception.EduException;
import com.app.springapp.repository.UserAttendanceDAO;
import com.app.springapp.service.UserExpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class})
public class UserAttendanceServiceImpl implements UserAttendanceService {
    private final UserAttendanceDAO userAttendanceDAO;
    private final RewardService rewardService;
    private final UserExpService userExpService;

    // 오늘 출석 등록 후 학습 출석 보상과 레벨 경험치를 각각 지급
    @Override
    public void checkIn(Long userId) {
        if (userAttendanceDAO.existsTodayAttendance(userId)) {
            throw new EduException("이미 오늘 출석을 완료했습니다.", HttpStatus.CONFLICT);
        }

        Long attendanceId = userAttendanceDAO.saveTodayAttendance(userId);

        // 팀원 학습 출석 보상 30은 보상 이력으로 지급
        rewardService.grantReward(userId, "ATTENDANCE", "DAILY", attendanceId);

        // 레벨업 출석 경험치 40은 별도 경험치 이력으로 지급
        userExpService.addAttendanceExp(userId);

        int consecutiveDays = userAttendanceDAO.findConsecutiveDays(userId);
        grantStreakReward(userId, consecutiveDays, attendanceId);
    }

    // 출석 현황 조회
    @Override
    public UserAttendanceSummaryResponseDTO getAttendanceSummary(Long userId, LocalDate startDate, LocalDate endDate) {
        // 여러 DAO 조회 결과를 화면용 DTO 하나로 조립
        boolean checkedToday = userAttendanceDAO.existsTodayAttendance(userId);
        int totalAttendanceDays = userAttendanceDAO.findTotalAttendance(userId);
        int streakDays = userAttendanceDAO.findConsecutiveDays(userId);
        List<LocalDate> attendanceDates = userAttendanceDAO.findAttendanceDatesByPeriod(userId, startDate, endDate);

        return UserAttendanceSummaryResponseDTO
                .builder()
                .checkedToday(checkedToday)
                .currentDate(LocalDate.now())
                .todayLabel(
                        checkedToday ? "오늘 출석을 완료했어요!" : "오늘의 출석체크를 진행해 주세요."
                )
                .streakDays(streakDays)
                .totalAttendanceDays(totalAttendanceDays)
                .attendanceDates(attendanceDates)
                .build();
    }

    // 연속 출석 달성 시 학습 보상 영역으로 추가 보상 지급
    private void grantStreakReward(Long userId, int consecutiveDays, Long attendanceId) {
        if (consecutiveDays == 3
                || consecutiveDays == 7
                || consecutiveDays == 14
                || consecutiveDays == 30) {
            rewardService.grantReward(
                    userId,
                    "ATTENDANCE",
                    "STREAK_" + consecutiveDays,
                    attendanceId
            );
        }
    }
}


