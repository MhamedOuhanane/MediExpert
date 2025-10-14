package com.mediexpert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediexpert.model.Calendrier;
import com.mediexpert.model.Demande;
import com.mediexpert.model.Indisponible;
import com.mediexpert.model.Specialiste;
import com.mediexpert.service.interfaces.SpecialisteService;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/specialist")
public class SpecialistServlet extends HttpServlet {
    private SpecialisteService specialisteService;

    @Override
    public void init() {
        this.specialisteService = (SpecialisteService) getServletContext().getAttribute("specialisteService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Specialiste specialiste = (Specialiste) SESSIONUtil.getUser(req);
        req.setAttribute("specialist", specialiste);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        List<Map<String, Object>> calendrierList = new ArrayList<>();
        if (specialiste != null) {
            for (Calendrier cal : specialiste.getCalendriers()) {
                Map<String, Object> calMap = new HashMap<>();
                calMap.put("date", cal.getDate());
                calMap.put("startTime", cal.getStartTime());
                calMap.put("endTime", cal.getEndTime());
                calMap.put("disponibilite", cal.getDisponibilite());

                Map<String, Object> indisponibles = new HashMap<>();
                for (Indisponible indi : cal.getIndisponibles()) {
                    indisponibles.put("startTime", indi.getStartTime());
                    indisponibles.put("endTime", indi.getEndTime());
                }
                calMap.put("indisponibles", indisponibles);

                Map<String, Object> reserves = new HashMap<>();
                for (Demande demand : specialiste.getDemandes()) {
                    if (demand.getStartDate().toLocalDate().equals(cal.getDate())) {
                        reserves.put("startTime", demand.getStartDate().toLocalTime());
                        reserves.put("endTime", demand.getStartDate().toLocalTime().plusMinutes(30));
                    }
                }
                calMap.put("reserves", reserves);
                calendrierList.add(calMap);
            }
        }

        String calendrierJson = mapper.writeValueAsString(calendrierList);
        req.setAttribute("calendrierJson", calendrierJson);
        req.setAttribute("currentRoute", "/specialist");
        req.getRequestDispatcher("pages/specialist/profile.jsp").forward(req, resp);
    }
}
