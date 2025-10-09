package com.mediexpert.repository.impl;

import com.mediexpert.model.User;
import com.mediexpert.repository.interfaces.UserRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> findByEmail(String email) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            User user = em.createQuery("SELECT u FROM users u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par email: " + e.getMessage(), e);
        }
    }
}
