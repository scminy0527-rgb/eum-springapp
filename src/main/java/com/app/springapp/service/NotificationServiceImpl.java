package com.app.springapp.service;

import com.app.springapp.domain.dto.NotificationDTO;
import com.app.springapp.domain.dto.UserDTO;
import com.app.springapp.domain.dto.response.SettingResponseDTO;
import com.app.springapp.repository.NotificationDAO;
import com.app.springapp.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationDAO notificationDAO;
    private final SimpMessagingTemplate messagingTemplate;
    private final EmailService emailService;
    private final UserDAO userDAO;  // 유저 이메일 조회용
    private final SettingService settingService;  // ← 추가

    @Override
    public void send(Long userId, String type, String name, String content, String url) {
        // 1. 유저 설정 확인
        SettingResponseDTO setting = settingService.getSetting(userId);

        boolean allowed = switch (type) {
            case "COMMUNITY_REPLY"  -> setting.getSettingReply() == 1;
            case "COMMUNITY_LIKE"   -> setting.getSettingGood() == 1;
            case "NOTICE"           -> setting.getSettingBulletin() == 1;
            case "LEARNING"         -> setting.getSettingStudy() == 1;
            case "INQUIRY_ANSWER"   -> setting.getSettingPushNotify() == 1;
            default -> true;
        };

        if (!allowed) {
            log.info("알림 설정 꺼져있음 - userId: {}, type: {}", userId, type);
            return;
        }

        // 2. DB 저장
        NotificationDTO dto = new NotificationDTO();
        dto.setUserId(userId);
        dto.setNotificationType(type);
        dto.setNotificationName(name);
        dto.setNotificationContent(content);
        dto.setNotificationUrl(url);
        notificationDAO.save(dto);

        // 3. WebSocket 실시간 전송
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications",
                dto
        );

        // 4. 이메일 발송 (이메일 수신 설정 켜진 경우만)
        if (setting.getSettingEmailPush() == 1) {
            try {
                UserDTO user = userDAO.findUserById(userId).orElse(null);
                if (user != null && user.getUserEmail() != null) {
                    emailService.sendNotificationEmail(
                            user.getUserEmail(),
                            "[이음] " + name,
                            content
                    );
                }
            } catch (Exception e) {
                log.error("이메일 발송 실패 - userId: {}", userId);
            }
        }

        log.info("알림 전송 - userId: {}, type: {}", userId, type);
    }

    @Override
    public List<NotificationDTO> getNotifications(Long userId) {
        return notificationDAO.findAllByUserId(userId);
    }

    @Override
    public int countUnread(Long userId) {
        return notificationDAO.countUnread(userId);
    }

    @Override
    public void markAsRead(Long id) {
        notificationDAO.markAsRead(id);
    }

    @Override
    public void markAllAsRead(Long userId) {
        notificationDAO.markAllAsRead(userId);
    }

    @Override
    public void delete(Long id) {
        notificationDAO.delete(id);
    }

}