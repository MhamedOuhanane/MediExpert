package com.mediexpert.controller;

import com.mediexpert.enums.ConsultationStatut;
import com.mediexpert.model.*;
import com.mediexpert.model.Record;
import com.mediexpert.service.interfaces.CalendrierService;
import com.mediexpert.util.CSRFUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/calendriers/*")
public class CalendrierServlet extends HttpServlet {
    private CalendrierService calendrierService;

    @Override
    public void init() throws ServletException {
        calendrierService = (CalendrierService) getServletContext().getAttribute("calendrierService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CSRFUtil.validationToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalide");
            return;
        }

        try {
            LocalDate date = LocalDate.parse(req.getParameter("date"));
            String specialiste_id = req.getParameter("specialiste_id");
            LocalTime startTime = LocalTime.parse(req.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(req.getParameter("endTime"));

            Calendrier calendrier = new Calendrier();
            Specialiste specialiste = new Specialiste();
            specialiste.setId(UUID.fromString(specialiste_id));

            calendrier.setSpecialiste(specialiste);
            calendrier.setDate(date);
            calendrier.setEndTime(endTime);
            calendrier.setStartTime(startTime);

            calendrier = calendrierService.addCalendrier(calendrier);

            req.getSession().setAttribute("successMessage", "Jour ouvrable ajouté avec succès");
            resp.sendRedirect(req.getContextPath() + "/specialist");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/specialist");
        }


    }
}
