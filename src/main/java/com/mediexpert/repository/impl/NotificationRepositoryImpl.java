package com.mediexpert.repository.impl;

import com.mediexpert.model.Demande;
import com.mediexpert.model.Notification;
import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.NotificationRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class NotificationRepositoryImpl implements NotificationRepository {
    @Override
    public Notification insert(Notification notification) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                notification.setDemande(em.find(Demande.class, notification.getDemande()));

                em.persist(notification);
                tx.commit();
                return notification;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du demande: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void readNotification(Specialiste specialiste) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.createQuery("Update Notification n set n.isRead = true LEFT JOIN FETCH n.demande d WHERE d.specialiste_id = :id")
                        .setParameter("id", specialiste.getId())
                        .executeUpdate();
                tx.commit();
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de read des notifications:" + e.getMessage(), e);
            }
        }
    }
}
