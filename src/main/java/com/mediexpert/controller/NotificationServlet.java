package com.mediexpert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mediexpert.model.Specialiste;
import com.mediexpert.service.interfaces.NotificationService;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/notifications/*")
public class NotificationServlet extends HttpServlet {
    private NotificationService notificationService;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {
        notificationService = (NotificationService) getServletContext().getAttribute("notificationService");
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Specialiste specialiste = (Specialiste) SESSIONUtil.getUser(req);
        String path = req.getPathInfo();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            if (path == null || path.isEmpty() || path.equals("/")) {
                notificationService.readSpecNotifications(specialiste);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"success\": true, \"message\": \"Notifications marquées comme lues\"}");

            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Route non trouvée\"}");
            }

        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Une erreur inattendue s'est produite\"}");
        }
    }
}