package com.mediexpert.repository.impl;

import com.mediexpert.model.ActesTechniques;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Record;
import com.mediexpert.repository.interfaces.ActesTechniquesRepository;
import com.mediexpert.repository.interfaces.ConsultationRepository;
import com.mediexpert.repository.interfaces.RecordRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    @Override
    public Consultation insertConsultation(Consultation consultation) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Record patient = em.find(Record.class, consultation.getRecord().getId());
                consultation.setRecord(patient);
                if (!consultation.getActesTechniques().isEmpty()) {
                    List<ActesTechniques> listActes = new ArrayList<>();
                    for (ActesTechniques act : consultation.getActesTechniques()) {
                        ActesTechniques actesTechniques = em.find(ActesTechniques.class, act.getId());
                        listActes.add(actesTechniques);
                    }
                    consultation.setActesTechniques(listActes);
                }
                em.persist(consultation);
                tx.commit();
                return consultation;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du consultation: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Consultation> findConsultationById(UUID consultationId) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(Consultation.class, consultationId));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du consultation d'id '" + consultationId + "': " + e.getMessage(), e);
        }
    }

    @Override
    public List<Consultation> selectConsultation() {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return em.createQuery("SELECT c From Consultation c LEFT JOIN FETCH c.actesTechniques", Consultation.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la selection des consultations " + e.getMessage(), e);
        }
    }

    @Override
    public Consultation updateConsultation(Consultation consultation) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Consultation updated = em.merge(consultation);
                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la modification du consultation:" + e.getMessage(), e);
            }
        }
    }

    @Override
    public Boolean deleteConsultation(Consultation consultation) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Consultation deleted = em.contains(consultation) ? consultation : em.merge(consultation);
                em.remove(deleted);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la suppression du consultation: " + e.getMessage(), e);
            }
        }
    }
}
