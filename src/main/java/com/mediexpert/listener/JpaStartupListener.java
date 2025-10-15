package com.mediexpert.listener;

import com.mediexpert.repository.impl.*;
import com.mediexpert.repository.interfaces.*;
import com.mediexpert.service.impl.*;
import com.mediexpert.service.interfaces.*;
import com.mediexpert.util.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JpaStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        EntityManager em =  DBUtil.getEntityManager();
//        em.close();
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

        RoleRepository roleRepository = new RoleRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        AdminRepository adminRepository = new AdminRepositoryImpl();
        SpecialisteRepository specialisteRepository = new SpecialisteRepositoryImpl();
        InfirmierRepository infirmierRepository = new InfirmierRepositoryImpl();
        GeneralisteRepository generalisteRepository = new GeneralisteRepositoryImpl();
        RecordRepository recordRepository = new RecordRepositoryImpl();
        ActesTechniquesRepository actRepo = new ActesTechniquesRepositoryImpl();
        ConsultationRepository consultationRepository = new ConsultationRepositoryImpl();
        IndisponibleRepository indisponibleRepository = new IndisponibleRepositoryImpl();

        RoleService roleService = new RoleServiceImpl(roleRepository);
        UserService userService = new UserServiceImpl(userRepository, specialisteRepository);
        AdminService adminService = new AdminServiceImpl(adminRepository);
        SpecialisteService specialisteService = new SpecialisteServiceImpl(specialisteRepository);
        GeneralisteService generalisteService = new GeneralisteServiceImpl(generalisteRepository);
        InfirmierService infirmierService = new InfirmierServiceImpl(infirmierRepository);
        RecordService recordService = new RecordServiceImpl(recordRepository);
        ActesTechniquesService actService = new ActesTechniquesServiceImpl(actRepo);
        ConsultationService consultationService = new ConsultationServiceImpl(consultationRepository, recordService);
        IndisponibleService indisponibleService = new IndisponibleServiceImpl(indisponibleRepository);

        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("adminService", adminService);
        sce.getServletContext().setAttribute("specialisteService", specialisteService);
        sce.getServletContext().setAttribute("generalisteService", generalisteService);
        sce.getServletContext().setAttribute("infirmierService", infirmierService);
        sce.getServletContext().setAttribute("recordService", recordService);
        sce.getServletContext().setAttribute("consultationService", consultationService);
        sce.getServletContext().setAttribute("roleService", roleService);
        sce.getServletContext().setAttribute("actService", actService);
        sce.getServletContext().setAttribute("indisponibleService", indisponibleService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBUtil.closeFactory();
    }
}
