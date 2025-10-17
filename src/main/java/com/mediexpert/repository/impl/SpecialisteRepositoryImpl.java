package com.mediexpert.repository.impl;

import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.SpecialisteRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpecialisteRepositoryImpl implements SpecialisteRepository {

    @Override
    public Specialiste insertSpecialiste(Specialiste specialiste) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(specialiste);
                tx.commit();
                return specialiste;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du spécialiste: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Specialiste> findSpecialiste(UUID specialisteId) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            Specialiste s = em.find(Specialiste.class, specialisteId);
            if (s != null) {
                Hibernate.initialize(s.getCalendriers());
                Hibernate.initialize(s.getDemandes());
                s.getDemandes().forEach(d -> Hibernate.initialize(d.getNotifications()));
            }
            return Optional.ofNullable(s);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du spécialiste d'id '" + specialisteId + "':" + e.getMessage(), e);
        }
    }

    @Override
    public List<Specialiste> selectSpecialiste() {
        try (EntityManager em = DBUtil.getEntityManager()) {
            List<Specialiste> specialistes = em.createQuery("SELECT s FROM Specialiste s", Specialiste.class)
                    .getResultList();
            specialistes.forEach(s -> {
                s.getCalendriers().size();
                s.getDemandes().size();
                s.getDemandes().forEach(d -> d.getNotifications().size());
            });
            return specialistes;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sélection des spécialistes: " + e.getMessage(), e);
        }
    }

    @Override
    public Specialiste updateSpecialiste(Specialiste specialiste) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Specialiste updated = em.merge(specialiste);
                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la mise à jour du spécialiste: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Boolean deleteSpecialiste(Specialiste specialiste) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Specialiste attached = em.contains(specialiste) ? specialiste : em.merge(specialiste);
                em.remove(attached);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la suppression du spécialiste: " + e.getMessage(), e);
            }
        }
    }
}
