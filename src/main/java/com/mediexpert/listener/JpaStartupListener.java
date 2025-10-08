package com.mediexpert.listener;

import com.mediexpert.util.DBUtil;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JpaStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DBUtil.getEntityManager();
        } catch (PersistenceException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBUtil.close();
        System.out.println("EntityManagerFactory closed.");
    }
}
