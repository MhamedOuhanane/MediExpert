package com.mediexpert.listener;

import com.mediexpert.model.Role;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;

//@WebListener
public class JpaStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        EntityManager em =  DBUtil.getEntityManager();

//        try {
//            em.getTransaction().begin();
//
//            em.persist(new Role("admin"));
//            em.persist(new Role("infirmier"));
//            em.persist(new Role("generaliste"));
//            em.persist(new Role("specialiste"));
//
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//        } finally {
//            em.close();
//        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBUtil.closeFactory();
        System.out.println("EntityManagerFactory closed.");
    }
}
