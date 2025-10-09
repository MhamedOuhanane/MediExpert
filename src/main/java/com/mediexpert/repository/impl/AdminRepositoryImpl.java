package com.mediexpert.repository.impl;

import com.mediexpert.model.Admin;
import com.mediexpert.repository.interfaces.AdminRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AdminRepositoryImpl implements AdminRepository {

    @Override
    public Admin insertAdmin(Admin admin) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(admin);
                tx.commit();
                return admin;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du spécialiste: " + e.getMessage(), e);
            }
        }
    }
}
