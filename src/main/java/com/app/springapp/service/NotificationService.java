package com.app.springapp.service;

import com.app.springapp.domain.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
    void send(Long userId, String type, String name, String content, String url);
    List<NotificationDTO> getNotifications(Long userId);
    int countUnread(Long userId);
    void markAsRead(Long id);
    void markAllAsRead(Long userId);
    void delete(Long id);
}