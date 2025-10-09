package com.mediexpert.repository.impl;

import com.mediexpert.model.Generaliste;
import com.mediexpert.repository.interfaces.GeneralisteRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GeneralisteRepositoryImpl implements GeneralisteRepository {


    @Override
    public Generaliste insertGeneraliste(Generaliste generaliste) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(generaliste);
                tx.commit();
                return generaliste;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du generaliste: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Generaliste> findGeneraliste(UUID generalisteId) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(Generaliste.class, generalisteId));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du generaliste d'id '" + generalisteId + "': " + e.getMessage(), e);
        }
    }

    @Override
    public List<Generaliste> selectGeneraliste() {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return em.createQuery("SELECT g From Generaliste g", Generaliste.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la selection des generalistes " + e.getMessage(), e);
        }
    }

    @Override
    public Generaliste updateGeneraliste(Generaliste generaliste) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Generaliste updated = em.merge(generaliste);
                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la modification du generaliste:" + e.getMessage(), e);
            }
        }
    }

    @Override
    public Boolean deleteGeneraliste(Generaliste generaliste) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Generaliste generaliste1 = em.contains(generaliste) ? generaliste : em.merge(generaliste);
                em.remove(generaliste1);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la suppression du generaliste: " + e.getMessage(), e);
            }
        }
    }
}
