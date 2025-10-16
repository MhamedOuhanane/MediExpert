package com.mediexpert.repository.impl;


import com.mediexpert.model.Consultation;
import com.mediexpert.model.Demande;
import com.mediexpert.model.Specialiste;
import com.mediexpert.repository.interfaces.DemandeRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DemandeRepositoryImpl implements DemandeRepository {
    @Override
    public Demande insert(Demande demande) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(demande);
                tx.commit();
                return demande;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du demande: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Demande> find(UUID demandeId) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            Demande demande1 = em.find(Demande.class, demandeId);
            Hibernate.initialize(demande1.getSpecialiste());
            Hibernate.initialize(demande1.getConsultation());
            return Optional.of(demande1);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du demande d'id '" + demandeId + "':" + e.getMessage(), e);
        }
    }

    @Override
    public Demande update(Demande demande) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Demande updated = em.merge(demande);
                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la modification du demande:" + e.getMessage(), e);
            }
        }
    }

    @Override
    public List<Demande> selectSpecialistDemand(Specialiste specialiste) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return em.createQuery("SELECT d FROM Demande d LEFT JOIN FETCH d.consultation c LEFT JOIN FETCH c.actesTechniques", Demande.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sélection des demandes: " + e.getMessage(), e);
        }
    }

}
