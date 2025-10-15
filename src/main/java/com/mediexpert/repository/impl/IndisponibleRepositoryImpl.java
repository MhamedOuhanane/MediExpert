package com.mediexpert.repository.impl;

import com.mediexpert.model.Calendrier;
import com.mediexpert.model.Indisponible;
import com.mediexpert.repository.interfaces.IndisponibleRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;
import java.util.UUID;

public class IndisponibleRepositoryImpl implements IndisponibleRepository {
    @Override
    public Indisponible insert(Indisponible indisponible) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Calendrier calendrier = em.find(Calendrier.class, indisponible.getCalendrier().getId());
                indisponible.setCalendrier(calendrier);
                em.persist(indisponible);
                tx.commit();
                return indisponible;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion d'indisponibilite: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Indisponible> find(UUID indisponibleID) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            Indisponible indisponible = em.find(Indisponible.class, indisponibleID);
            return Optional.ofNullable(indisponible);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'indisponibilite d'id '" + indisponibleID + "': " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Indisponible indisponible) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Indisponible indisponible1 = em.contains(indisponible) ? indisponible : em.merge(indisponible);
                em.remove(indisponible1);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la suppression d'indisponibilite: " + e.getMessage(), e);
            }
        }
    }
}
