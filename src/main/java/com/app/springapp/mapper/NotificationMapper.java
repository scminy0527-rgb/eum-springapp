package com.app.springapp.mapper;

import com.app.springapp.domain.dto.NotificationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    public void insert(NotificationDTO notificationDTO);
    public List<NotificationDTO> selectAllByUserId(Long userId);
    public int countUnread(Long userId);
    public void markAsRead(Long id);
    public void markAllAsRead(Long userId);
    public void softDelete(Long id);
    public void updateAllReadByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
    public int countUnreadByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
}