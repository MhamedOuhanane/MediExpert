package com.mediexpert.repository.impl;

import com.mediexpert.model.Calendrier;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.CalendrierRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CalendrierRepositoryImpl implements CalendrierRepository {
    @Override
    public Calendrier insert(Calendrier calendrier) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                calendrier.setSpecialiste(em.find(Specialiste.class, calendrier.getSpecialiste().getId()));

                em.persist(calendrier);
                tx.commit();
                return calendrier;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du calendrier: " + e.getMessage(), e);
            }
        }
    }
}
