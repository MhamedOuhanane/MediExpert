package com.mediexpert.repository.impl;

import com.mediexpert.model.Record;
import com.mediexpert.repository.interfaces.RecordRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecordRepositoryImpl implements RecordRepository {


    @Override
    public Record insertRecord(Record patient) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(patient);
                tx.commit();
                return patient;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du patient: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Record> findRecordById(UUID patientId) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(Record.class, patientId));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du patient d'id '" + patientId + "': " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Record> findRecordByCard(String recordCardCode) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            Record record = em.createQuery("SELECT r FROM Record r WHERE r.carte = :carte", Record.class)
                    .setParameter("carte", recordCardCode)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            return Optional.ofNullable(record);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du patient de carte '" + recordCardCode + "': " + e.getMessage(), e);
        }
    }

    @Override
    public List<Record> selectRecord() {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return em.createQuery("SELECT g From Record g", Record.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la selection des patients " + e.getMessage(), e);
        }
    }

    @Override
    public Record updateRecord(Record patient) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Record updated = em.merge(patient);
                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la modification du patient:" + e.getMessage(), e);
            }
        }
    }

    @Override
    public Boolean deleteRecord(Record patient) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Record patient1 = em.contains(patient) ? patient : em.merge(patient);
                em.remove(patient1);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la suppression du patient: " + e.getMessage(), e);
            }
        }
    }
}
