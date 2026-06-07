package com.app.springapp.scheduler;

import com.app.springapp.repository.UserDAO;
import com.app.springapp.service.NotificationService;
import com.app.springapp.mapper.UserAttendanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AttendanceNotificationScheduler {

    private final UserDAO userDAO;
    private final UserAttendanceMapper userAttendanceMapper;
    private final NotificationService notificationService;

    // 매일 오전 10시에 실행
    @Scheduled(cron = "0 0 10 * * *")
//    @Scheduled(cron = "0 */1 * * * *")
    public void sendAttendanceReminder() {
        log.info("출석 알림 스케줄러 실행");
        List<Long> userIds = userDAO.findAllUserIds();

        for (Long userId : userIds) {
            LocalDate lastDate = userAttendanceMapper.selectLastAttendanceDate(userId);
            if (lastDate == null) continue;

            long daysSince = java.time.temporal.ChronoUnit.DAYS.between(lastDate, LocalDate.now());

            if (daysSince >= 7) {   // 7일 이상 학습 안 했을 때
                notificationService.send(
                        userId,
                        "LEARNING",
                        "학습하러 돌아오세요! 👋",
                        daysSince + "일 동안 학습을 안 하셨어요. 오늘 수어 학습을 시작해보세요!",
                        "/edu"
                );
            }
        }
    }
}