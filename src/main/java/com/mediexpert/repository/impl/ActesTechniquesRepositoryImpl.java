package com.mediexpert.repository.impl;

import com.mediexpert.model.Consultation;
import com.mediexpert.model.ActesTechniques;
import com.mediexpert.repository.interfaces.ActesTechniquesRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ActesTechniquesRepositoryImpl implements ActesTechniquesRepository {


    @Override
    public ActesTechniques insertActesTechniques(ActesTechniques acteTechnique) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(acteTechnique);
                tx.commit();
                return acteTechnique;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du acte Technique: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<ActesTechniques> findActesTechniquesById(UUID acteTechniqueId) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(ActesTechniques.class, acteTechniqueId));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du acte Technique d'id '" + acteTechniqueId + "': " + e.getMessage(), e);
        }
    }

    @Override
    public List<ActesTechniques> selectActesTechniques() {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return em.createQuery("SELECT g From ActesTechniques g", ActesTechniques.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la selection des acte Techniques " + e.getMessage(), e);
        }
    }

    @Override
    public ActesTechniques updateActesTechniques(ActesTechniques acteTechnique) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                ActesTechniques updated = em.merge(acteTechnique);
                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la modification du acte Technique:" + e.getMessage(), e);
            }
        }
    }

    @Override
    public Boolean deleteActesTechniques(ActesTechniques acteTechnique) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                ActesTechniques acteTechnique1 = em.contains(acteTechnique) ? acteTechnique : em.merge(acteTechnique);
                em.remove(acteTechnique1);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la suppression du acte Technique: " + e.getMessage(), e);
            }
        }
    }
}
