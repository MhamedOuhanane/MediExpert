package com.mediexpert.repository.interfaces;

import com.mediexpert.model.Notification;
import com.mediexpert.model.Specialiste;

import java.util.List;

public interface NotificationRepository {
    Notification insert(Notification notification);
    void readNotification(Specialiste specialiste);
}
