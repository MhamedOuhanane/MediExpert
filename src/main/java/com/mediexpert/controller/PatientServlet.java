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
                String nom = req.getParameter("nom");
                String prenom = req.getParameter("prenom");
                LocalDate dateNaissance = LocalDate.parse(req.getParameter("dateNaissance"));
                String carte = req.getParameter("carte");
                String telephone = req.getParameter("telephone");
                Integer tension = Integer.valueOf(req.getParameter("tension"));
                Integer frequenceCardiaque = Integer.valueOf(req.getParameter("frequenceCardiaque"));
                Double temperature = Double.valueOf(req.getParameter("temperature"));
                Integer frequenceRespiratoire = Integer.valueOf(req.getParameter("frequenceRespiratoire"));
                Double poids = Double.valueOf(req.getParameter("poids"));
                Double taille = Double.valueOf(req.getParameter("taille"));
                StatusPatient status = StatusPatient.EN_ATTENTE;

                Record record = new Record();
                record.setNom(nom);
                record.setPrenom(prenom);
                record.setDateNaissance(dateNaissance);
                record.setCarte(carte);
                record.setTelephone(telephone);
                record.setTension(tension);
                record.setFrequenceCardiaque(frequenceCardiaque);
                record.setTemperature(temperature);
                record.setFrequenceRespiratoire(frequenceRespiratoire);
                record.setPoids(poids);
                record.setTaille(taille);
                record.setStatus(status);

                Record created = this.recordService.addRecord(record);
                req.setAttribute("currentRoute", "/patients");
                req.setAttribute("successMessage", "Le partient est ajouter avec success");
                req.getRequestDispatcher(req.getContextPath() + "/pages/infirmier/patients-list.jsp").forward(req, resp);
                return;
            } else if (path.equals("/update")) {

                String carte = req.getParameter("carte");
                String telephone = req.getParameter("telephone");
                Integer tension = Integer.valueOf(req.getParameter("tension"));
                Integer frequenceCardiaque = Integer.valueOf(req.getParameter("frequenceCardiaque"));
                Double temperature = Double.valueOf(req.getParameter("temperature"));
                Integer frequenceRespiratoire = Integer.valueOf(req.getParameter("frequenceRespiratoire"));
                Double poids = Double.valueOf(req.getParameter("poids"));
                Double taille = Double.valueOf(req.getParameter("taille"));

                Record record = new Record();

                record.setCarte(carte);
                record.setTelephone(telephone);
                record.setTension(tension);
                record.setFrequenceCardiaque(frequenceCardiaque);
                record.setTemperature(temperature);
                record.setFrequenceRespiratoire(frequenceRespiratoire);
                record.setPoids(poids);
                record.setTaille(taille);


                Record created = this.recordService.updateRecord(record);

                req.setAttribute("currentRoute", "/patients");
                req.setAttribute("successMessage", "Modifier les information de partient " + created.getNom() + " " + created.getPrenom() + " avec success");
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
