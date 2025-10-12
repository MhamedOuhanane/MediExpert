package com.mediexpert.controller;

import com.mediexpert.enums.StatusPatient;
import com.mediexpert.model.Record;
import com.mediexpert.model.User;
import com.mediexpert.service.interfaces.RecordService;
import com.mediexpert.util.CSRFUtil;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/patients/*")
public class PatientServlet extends HttpServlet {
    private RecordService recordService;

    @Override
    public void init() {
        this.recordService = (RecordService) getServletContext().getAttribute("recordService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String csrfToken = CSRFUtil.getCsrfToken(req.getSession(false));
        if (csrfToken == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token manquant");
            return;
        }
        User user = SESSIONUtil.getUser(req);
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();

        if (path.equals("/")) {
            var records = this.recordService.getAllRecord();
            String url = "/pages/infirmier/patients-list.jsp";
            if (user != null && user.getRole().getName().equals("generaliste")) {
                records = records.stream()
                        .filter(patient -> patient.getStatus().equals(StatusPatient.EN_ATTENTE))
                        .toList();
                url = "/pages/generalist/patients-list.jsp";
            }
            req.setAttribute("patients", records);
            req.setAttribute("currentRoute", "/patients");
            req.getRequestDispatcher(url).forward(req, resp);
            return;
        }

        if (path.equals("/add")) {
            req.setAttribute("csrfToken", csrfToken);
            req.setAttribute("record", null);
            req.setAttribute("currentRoute", "/patients/add");
            req.getRequestDispatcher("/pages/infirmier/add-patient-form.jsp").forward(req, resp);
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

        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();

        try {
            if (path.equals("/find")) {
                String carte = req.getParameter("carte");
                String redirectTo = req.getParameter("redirectTo");
                var record = this.recordService.findRecordByCard(carte);
                if (record != null) {
                    StatusPatient statusPatient = record.getStatus();
                    if (statusPatient.equals(StatusPatient.EN_COURS)) {
                        req.getSession().setAttribute("errorMessage", "Le patient qui détient actuellement une carte '" + record.getCarte() + "' est consulté et ne peut pas la modifier.!");
                        resp.sendRedirect(req.getContextPath() + "/patients");
                        return;
                    }
                }
                req.setAttribute("existence", record != null);
                req.setAttribute("record", record);
                req.setAttribute("carte", carte);
                req.setAttribute("csrfToken", CSRFUtil.getCsrfToken(req.getSession()));
                if (redirectTo == null || redirectTo.isEmpty()) {
                    redirectTo = "/pages/infirmier/add-patient-form.jsp";
                }
                req.getRequestDispatcher(redirectTo).forward(req, resp);
                return;
            }

            if (path.equals("/add")) {
                Record record = new Record();
                record.setNom(req.getParameter("nom"));
                record.setPrenom(req.getParameter("prenom"));
                record.setDateNaissance(LocalDate.parse(req.getParameter("dateNaissance")));
                record.setCarte(req.getParameter("carte"));
                record.setTelephone(req.getParameter("telephone"));
                record.setTension(Integer.valueOf(req.getParameter("tension")));
                record.setFrequenceCardiaque(Integer.valueOf(req.getParameter("frequenceCardiaque")));
                record.setTemperature(Double.valueOf(req.getParameter("temperature")));
                record.setFrequenceRespiratoire(Integer.valueOf(req.getParameter("frequenceRespiratoire")));
                record.setPoids(Double.valueOf(req.getParameter("poids")));
                record.setTaille(Double.valueOf(req.getParameter("taille")));
                record.setStatus(StatusPatient.EN_ATTENTE);

                Record record1 = this.recordService.addRecord(record);
                req.getSession().setAttribute("successMessage", "Le patient de carte '" + record1.getCarte() + "' a été ajouté avec succès !");
                resp.sendRedirect(req.getContextPath() + "/patients");
                return;
            }
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/patients/add");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();
        try {
            if (path.equals("/update")) {
                Record record = new Record();
                record.setCarte(req.getParameter("carte"));
                record.setTelephone(req.getParameter("telephone"));
                record.setTension(Integer.valueOf(req.getParameter("tension")));
                record.setFrequenceCardiaque(Integer.valueOf(req.getParameter("frequenceCardiaque")));
                record.setTemperature(Double.valueOf(req.getParameter("temperature")));
                record.setFrequenceRespiratoire(Integer.valueOf(req.getParameter("frequenceRespiratoire")));
                record.setPoids(Double.valueOf(req.getParameter("poids")));
                record.setTaille(Double.valueOf(req.getParameter("taille")));

                this.recordService.updateRecord(record);
                req.getSession().setAttribute("successMessage", "Les informations du patient de carte '" + record.getCarte() + "' ont été mises à jour !");
                resp.sendRedirect(req.getContextPath() + "/patients");
                return;
            }

            if (path.equals("/status")) {
                UUID id = UUID.fromString(req.getParameter("id"));
                StatusPatient status = StatusPatient.valueOf(req.getParameter("status"));
                this.recordService.updateStatus(id, status);
                req.getSession().setAttribute("successMessage", "Statut du patient modifié !");
                resp.sendRedirect(req.getContextPath() + "/patients");
            }

        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/patients");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UUID id = UUID.fromString(req.getParameter("id"));
            boolean deleted = recordService.deleteRecord(id);
            if (deleted)
                req.getSession().setAttribute("successMessage", "Patient supprimé avec succès !");
            else
                req.getSession().setAttribute("errorMessage", "Erreur lors de la suppression !");
            resp.sendRedirect(req.getContextPath() + "/patients");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/patients");
        }
    }
}
