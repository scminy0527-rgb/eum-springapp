package com.app.springapp.scheduler;

import com.app.springapp.repository.NotificationDAO;
import com.app.springapp.repository.ReviewDAO;
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
    private final ReviewDAO reviewDAO;
    private final NotificationDAO notificationDAO;

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

//    @Scheduled(cron = "0 0 10 * * *")
@Scheduled(cron = "0 */1 * * * *")
public void sendReviewReminder() {
    log.info("리뷰 알림 스케줄러 실행");
    List<Long> userIds = userDAO.findAllUserIds();

    for (Long userId : userIds) {
        // 이미 안읽은 REVIEW 알림이 있으면 스킵
        int unreadReview = notificationDAO.countUnreadByUserIdAndType(userId, "REVIEW");
        if (unreadReview > 0) continue;  // ← 추가

        LocalDate lastReviewDate = reviewDAO.findLastReviewDate(userId);

        if (lastReviewDate == null ||
                java.time.temporal.ChronoUnit.DAYS.between(lastReviewDate, LocalDate.now()) >= 7) {
            notificationService.send(
                    userId,
                    "REVIEW",
                    "후기를 작성해주세요! ⭐",
                    "수업은 어떠셨나요? 후기를 남겨주세요.",
                    null
                );
            }
        }
    }
}