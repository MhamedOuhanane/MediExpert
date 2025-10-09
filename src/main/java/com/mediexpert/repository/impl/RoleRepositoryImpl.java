package com.mediexpert.repository.impl;

import com.mediexpert.model.Role;
import com.mediexpert.model.Role;
import com.mediexpert.repository.interfaces.RoleRepository;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoleRepositoryImpl implements RoleRepository {


    @Override
    public Role insertRole(Role role) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                em.persist(role);
                tx.commit();
                return role;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de l'insertion du role: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Role> findRoleById(UUID roleId) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return Optional.ofNullable(em.find(Role.class, roleId));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du role d'id '" + roleId + "': " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            Role role = em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            return Optional.ofNullable(role);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du role de nom '" + name + "': " + e.getMessage(), e);
        }
    }

    @Override
    public List<Role> selectRole() {
        try (EntityManager em = DBUtil.getEntityManager()) {
            return em.createQuery("SELECT g From Role g", Role.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la selection des roles " + e.getMessage(), e);
        }
    }

    @Override
    public Role updateRole(Role role) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Role updated = em.merge(role);
                tx.commit();
                return updated;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la modification du role:" + e.getMessage(), e);
            }
        }
    }

    @Override
    public Boolean deleteRole(Role role) {
        try (EntityManager em = DBUtil.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Role deleted = em.contains(role) ? role : em.merge(role);
                em.remove(deleted);
                tx.commit();
                return true;
            } catch (Exception e) {
                if (tx.isActive()) tx.rollback();
                throw new RuntimeException("Erreur lors de la suppression du role: " + e.getMessage(), e);
            }
        }
    }
}
