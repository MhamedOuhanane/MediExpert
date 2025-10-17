package com.mediexpert.service.interfaces;

import com.mediexpert.model.Notification;
import com.mediexpert.model.Specialiste;

import java.util.List;

public interface NotificationService {
    Notification addNotification(Notification notification);
    void readSpecNotifications(Specialiste specialiste);
}