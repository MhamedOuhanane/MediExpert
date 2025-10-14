package com.mediexpert.controller;

import com.mediexpert.enums.ConsultationStatut;
import com.mediexpert.enums.StatusPatient;
import com.mediexpert.model.ActesTechniques;
import com.mediexpert.model.Consultation;
import com.mediexpert.model.Record;
import com.mediexpert.model.User;
import com.mediexpert.service.interfaces.ConsultationService;
import com.mediexpert.util.CSRFUtil;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/consultations/*")
public class ConsultationServlet extends HttpServlet {
    private ConsultationService consultationService;

    @Override
    public void init() throws ServletException {
        consultationService = (ConsultationService) getServletContext().getAttribute("consultationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SESSIONUtil.getUser(req);
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();

        if (path.equals("/")) {
            var consultations = this.consultationService.getAllConsultation();
            for (Consultation c : consultations) {
                c.getActesTechniques().size();
            }
            String url = "/pages/generalist/consultations.jsp";
            if (user != null && user.getRole().getName().equals("specialiste")) {
                consultations = consultations.stream()
                        .filter(consul -> consul.getStatut().equals(ConsultationStatut.EN_ATTENTE_AVIS_SPECIALISTE))
                        .toList();

                url = "/pages/generalist/consultation.jsp";
            }
            req.setAttribute("consultations", consultations);
            req.setAttribute("currentRoute", "/consultations");
            req.getRequestDispatcher(url).forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CSRFUtil.validationToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalide");
            return;
        }

        String method = req.getParameter("_method");
        if (method != null) {
            if ("PUT".equalsIgnoreCase(method)) {
                doPut(req, resp);
                return;
            } else if ("DELETE".equalsIgnoreCase(method)) {
                doDelete(req, resp);
                return;
            }
        }

        String path = req.getPathInfo() != null ? req.getPathInfo() : "/";
        try {

            Record record = new Record();
            Consultation consultation = new Consultation();

            consultation.setRecord(record);
            record.setId(UUID.fromString(req.getParameter("recordId")));
            consultation.setRaison(req.getParameter("raison"));
            consultation.setObservations(req.getParameter("observations"));
            consultation.setPrix(Double.parseDouble(req.getParameter("prix")));
            ConsultationStatut status = "/add".equals(path) ? ConsultationStatut.TERMINEE : ConsultationStatut.EN_ATTENTE_AVIS_SPECIALISTE;
            consultation.setStatut(status);
            List<ActesTechniques> listAct = new ArrayList<>();
            String[] actsId = req.getParameterValues("actesTechniques");
            for (String actId : actsId) {
                ActesTechniques actesTechniques = new ActesTechniques();
                actesTechniques.setId(UUID.fromString(actId));
                listAct.add(actesTechniques);
            }
            if (!listAct.isEmpty()) consultation.setActesTechniques(listAct);
            Consultation consultation1 = consultationService.addConsultation(consultation);
            req.getSession().setAttribute("successMessage", "Le consultation de patient de carte '" + consultation1.getRecord().getCarte() + "' a été ajouté avec succès !");
            resp.sendRedirect(req.getContextPath() + "/patients");
            return;
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/patients");
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
