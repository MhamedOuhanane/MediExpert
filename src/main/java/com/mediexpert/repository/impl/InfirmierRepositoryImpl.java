package com.mediexpert.repository.impl;

import com.mediexpert.model.Infirmier;
import com.mediexpert.repository.interfaces.InfirmierRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InfirmierRepositoryImpl implements InfirmierRepository {


    @Override
    public Infirmier insertInfirmier(Infirmier infirmier) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(infirmier);
                tx.commit();
                return infirmier;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion d'infirmier: " + e.getMessage(), e);
            }

        }
    }

    @Override
    public Optional<Infirmier> findInfirmier(UUID infirmierId) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(Infirmier.class, infirmierId));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche d'infirmier d'id '" + infirmierId + "': " + e.getMessage(), e);
        }
    }

    @Override
    public List<Infirmier> selectInfirmier() {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return em.createQuery("SELECT i FROM Infirmier i", Infirmier.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la selection des infirmiers " + e.getMessage(), e);
        }
    }

    @Override
    public Infirmier updateInfirmier(Infirmier infirmier) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Infirmier updated = em.merge(infirmier);
                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la modification d'infirmier:" + e.getMessage(), e);
            }

        }
    }

    @Override
    public Boolean deleteInfirmier(Infirmier infirmier) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Infirmier deleted = em.contains(infirmier) ? infirmier : em.merge(infirmier);
                em.remove(deleted);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la suppression d'infirmier: " + e.getMessage(), e);
            }

        }
    }
}
