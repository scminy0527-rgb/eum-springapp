package com.app.springapp.repository;

import com.app.springapp.domain.dto.NotificationDTO;
import com.app.springapp.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationDAO {
    private final NotificationMapper notificationMapper;

    public void save(NotificationDTO notificationDTO) {
        notificationMapper.insert(notificationDTO);
    }

    public List<NotificationDTO> findAllByUserId(Long userId) {
        return notificationMapper.selectAllByUserId(userId);
    }

    public int countUnread(Long userId) {
        return notificationMapper.countUnread(userId);
    }

    public void markAsRead(Long id) {
        notificationMapper.markAsRead(id);
    }

    public void markAllAsRead(Long userId) {
        notificationMapper.markAllAsRead(userId);
    }

    public void delete(Long id) {
        notificationMapper.softDelete(id);
    }

    public void markAllReadByUserIdAndType(Long userId, String type) {
        notificationMapper.updateAllReadByUserIdAndType(userId, type);
    }

    public int countUnreadByUserIdAndType(Long userId, String type) {
        return notificationMapper.countUnreadByUserIdAndType(userId, type);
    }
}