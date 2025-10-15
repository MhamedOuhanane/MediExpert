package com.mediexpert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediexpert.model.Calendrier;
import com.mediexpert.model.Indisponible;
import com.mediexpert.service.interfaces.IndisponibleService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet("/indisponibles/*")
public class IndisponibleServlet extends HttpServlet {
    private IndisponibleService indisponibleService;

    @Override
    public void init() {
        this.indisponibleService = (IndisponibleService) getServletContext().getAttribute("indisponibleService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            while ((line = req.getReader().readLine()) != null) {
                jsonBuffer.append(line);
            }

            Map<String, Object> data = mapper.readValue(jsonBuffer.toString(), Map.class);

            String _methode = (String) data.get("_methode");
            if (_methode.equals("DELETE")) {
                req.setAttribute("id", data.get("id"));
                doDelete(req, resp);
                return;
            }

            LocalTime startTime = LocalTime.parse(data.get("startTime").toString());
            UUID calendrier_id = UUID.fromString(data.get("calendrier_id").toString());

            Calendrier calendrier = new Calendrier();
            Indisponible indisponible = new Indisponible();
            calendrier.setId(calendrier_id);
            indisponible.setCalendrier(calendrier);
            indisponible.setStartTime(startTime);
            indisponible.setEndTime(startTime.plusMinutes(30));

            indisponible = indisponibleService.addIndisponible(indisponible);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", indisponible.getId());
            responseData.put("startTime", indisponible.getStartTime().toString());
            responseData.put("endTime", indisponible.getEndTime().toString());
            responseData.put("calendrierId", indisponible.getCalendrier().getId());
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(responseData));
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": " + e.getMessage() + "}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"ID est vide\"}");
            return;
        }
        UUID id = UUID.fromString(pathInfo.substring(1));

        boolean deleted = indisponibleService.deleteIndisponible(id);

        resp.setContentType("application/json");
        resp.getWriter().write("{\"deleted\": " + deleted + "}");
    }
}
