package com.mediexpert.controller;

import com.mediexpert.enums.StatusPatient;
import com.mediexpert.model.Record;
import com.mediexpert.service.interfaces.RecordService;
import com.mediexpert.util.CSRFUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

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
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token pas exist");
            return;
        }
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();
        if (path.equals("/")) {
            var records = this.recordService.getAllRecord();
            req.setAttribute("records", records);
            req.setAttribute("currentRoute", "/patients");
            req.getRequestDispatcher(req.getContextPath() + "/pages/infirmier/patients-list.jsp").forward(req, resp);
            return;
        } else if (path.equals("/add")){
            req.setAttribute("csrfToken", csrfToken);
            req.setAttribute("patient", null);
            req.setAttribute("currentRoute", "/patients/add");
            req.getRequestDispatcher(req.getContextPath() + "/pages/infirmier/add-patient-form.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CSRFUtil.validationToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalide");
            return;
        }
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();

        try {
            if (path.equals("/find")) {
                var carte = req.getParameter("carte");
                var record = this.recordService.findRecordByCard(carte);
                req.setAttribute("existence", record != null);
                req.setAttribute("record", record);
                req.setAttribute("currentRoute", "/patients/add");
                req.getRequestDispatcher(req.getContextPath() + "/pages/infirmier/add-patient-form.jsp").forward(req, resp);
                return;
            } else if (path.equals("/add")){
                Record record = new Record();
                record.setNom(req.getParameter("nom"));
                record.setPrenom(req.getParameter("prenom"));
                record.setDateNaissance(LocalDate.parse(req.getParameter("dateNaissance")));
                record.setCarte(req.getParameter("carte"));
                record.setTelephone(req.getParameter("telephone"));
                record.setTension(req.getParameter("tension"));
                record.setFrequenceCardiaque(Integer.valueOf(req.getParameter("frequenceCardiaque")));
                record.setTemperature(Double.valueOf(req.getParameter("temperature")));
                record.setFrequenceRespiratoire(Integer.valueOf(req.getParameter("frequenceRespiratoire")));
                record.setPoids(Double.valueOf(req.getParameter("poids")));
                record.setTaille(Double.valueOf(req.getParameter("taille")));
                record.setStatusPatient(StatusPatient.EN_ATTENTE);

                Record created = this.recordService.addRecord(record);
                req.setAttribute("currentRoute", "/patients");
                req.setAttribute("successMessage", "Le partient est ajouter avec success");
                req.getRequestDispatcher(req.getContextPath() + "/pages/infirmier/patients-list.jsp").forward(req, resp);
                return;
            }
        } catch (RuntimeException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("currentRoute", "/patients/add");
            req.getRequestDispatcher(req.getContextPath() + "/pages/infirmier/add-patient-form.jsp").forward(req, resp);
        }
    }
}
