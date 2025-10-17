package com.mediexpert.service.impl;

import com.mediexpert.model.Demande;
import com.mediexpert.model.Notification;
import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.NotificationRepository;
import com.mediexpert.service.interfaces.NotificationService;

import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification addNotification(Notification notification) {
        if (notification == null) throw new IllegalArgumentException("La notification ne peut pas être null.");
        if (notification.getDemande() == null) throw new IllegalArgumentException("Le demande notifies ne peut pas être null.");
        return notificationRepository.insert(notification);
    }

    @Override
    public void readSpecNotifications(Specialiste specialiste) {
        if (specialiste == null) throw new IllegalArgumentException("Le specialist ne peut pas être null.");
        notificationRepository.readNotification(specialiste);
    }
}
