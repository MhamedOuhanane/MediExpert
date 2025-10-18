package com.mediexpert.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediexpert.enums.DemandeStatut;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Demande;
import com.mediexpert.model.Specialiste;
import com.mediexpert.model.User;
import com.mediexpert.service.interfaces.ConsultationService;
import com.mediexpert.service.interfaces.DemandeService;
import com.mediexpert.service.interfaces.SpecialisteService;
import com.mediexpert.util.CSRFUtil;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/demandes/*")
public class DemandeServlet extends HttpServlet {
    private DemandeService demandeService;
    private SpecialisteService specialisteService;

    @Override
    public void init() throws ServletException {
        this.demandeService = (DemandeService) getServletContext().getAttribute("demandeService");
        this.specialisteService = (SpecialisteService) getServletContext().getAttribute("specialisteService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SESSIONUtil.getUser(req);
        String path = req.getPathInfo() == null ? "" : req.getPathInfo();

        try {
            if (path.isEmpty() || path.equals("/")) {
                var demands = demandeService.getSpecDemande((Specialiste) user);
                req.setAttribute("demands", demands);
                req.setAttribute("currentRoute", "/demandes");
                req.getRequestDispatcher(req.getContextPath() + "/pages/specialist/demandes.jsp").forward(req, resp);
                return;
            }
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            req.setAttribute("currentRoute", "/demandes");
            req.getRequestDispatcher(req.getContextPath() + "/pages/specialist/demandes.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String csrf = req.getParameter("csrfToken");
        if (!CSRFUtil.validationToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalide");
            return;
        }
        String _methode = req.getParameter("_methode");
        if (_methode != null) {
            if (_methode.equals("PUT")) {
                doPut(req, resp);
                return;
            }
        }
        String path = req.getPathInfo() == null ? "" : req.getPathInfo() ;

        try {
            if (path.equals("/demand")) {
                demand(req, resp);
                return;
            } else if (path.isEmpty() || path.equals("/")) {
                UUID consultationId = UUID.fromString(req.getParameter("consultationId"));
                String question = req.getParameter("question");
                UUID specialisteId = UUID.fromString(req.getParameter("specialisteId"));
                double prix = Double.parseDouble(req.getParameter("prix"));
                LocalDateTime startDate = LocalDateTime.parse(req.getParameter("startDate"));
                Demande demande = new Demande();
                Consultation consultation = new Consultation();
                Specialiste specialiste = new Specialiste();
                specialiste.setId(specialisteId);
                consultation.setId(consultationId);
                demande.setSpecialiste(specialiste);
                demande.setConsultation(consultation);
                demande.setQuestion(question);
                demande.setPrix(prix);
                demande.setStartDate(startDate);
                demande.setStatut(DemandeStatut.EN_ATTENTE_AVIS_SPECIALISTE);

                Demande demande1 = demandeService.addDemande(demande);
                req.getSession().setAttribute("successMessage", "Le Demande du consultation de patient de carte '" + demande1.getConsultation().getRecord().getCarte() + "' a été ajouté avec succès !");
                resp.sendRedirect(req.getContextPath() + "/consultations");
            }
        } catch (RuntimeException e) {
            resp.sendRedirect(req.getContextPath() + "/consultations");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() == null ? "" : req.getPathInfo() ;
        try {
            String demandeId = req.getParameter("demandeId");
            String response = req.getParameter("response");
            if (response == null || response.isEmpty() || demandeId == null || demandeId.isEmpty()) throw new IllegalArgumentException("response ne peut pas etre vide");
            if (path.equals("/respond")) {
                Demande demande = demandeService.responseDemand(UUID.fromString(demandeId), response, DemandeStatut.TERMINEE);
            }
            req.getSession().setAttribute("successMessage", "Le demande validé avec succès !");
            resp.sendRedirect(req.getContextPath() + "/demandes");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/demandes");
            return;
        }
    }

    private void demand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String consultationId = req.getParameter("consultationId");
        try {
            UUID conID = UUID.fromString(consultationId);
            List<Specialiste> specialistes = specialisteService.getAllSpecialiste();
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            Map<String, Object> calendriersMap = new HashMap<>();
            for (Specialiste spec : specialistes) {
                List<Map<String, Object>> calendrierJson = specialisteService.calendrierJson(spec);
                calendriersMap.put(spec.getId().toString(), calendrierJson);
            }

            req.setAttribute("specialistes", specialistes);
            req.setAttribute("calendriersMap", mapper.writeValueAsString(calendriersMap));
            req.setAttribute("consultationId", conID);
            req.getRequestDispatcher(req.getContextPath() + "/pages/generalist/demand.jsp").forward(req, resp);
        } catch (RuntimeException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher(req.getContextPath() + "/pages/specialist/demandes.jsp").forward(req, resp);
        }
    }
}
