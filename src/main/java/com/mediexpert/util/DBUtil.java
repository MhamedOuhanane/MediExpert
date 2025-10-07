package com.mediexpert.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class DBUtil {
    private static final EntityManagerFactory emf;

    static {

        String dbpassword = System.getenv("DB_PASSWORD");
        // ⚠️ IMPORTANT :
        // Avant d’exécuter l’application, tu dois définir la variable d’environnement DB_PASSWORD
        // pour éviter d’écrire le mot de passe directement dans le code.
        //
        // Ouvre PowerShell et exécute cette commande :
        //     setx DB_PASSWORD "tonMotDePasseIci"
        //
        // Ensuite, redémarre ton terminal ou ton IDE (Eclipse, IntelliJ, VS Code…)
        // pour que la variable soit prise en compte.

        Map<String, String> propertys = new HashMap<>();
        propertys.put("jakarta.persistence.jdbc.password", dbpassword);
        emf = Persistence.createEntityManagerFactory("MediExpert", propertys);
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
