package com.mediexpert.service.interfaces;

import com.mediexpert.model.Notification;

public interface NotificationService {
    Notification addNotification(Notification notification);
    void readNotifications();
}