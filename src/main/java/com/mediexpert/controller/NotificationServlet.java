package com.mediexpert.controller;

import com.mediexpert.service.interfaces.NotificationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/notifications/*")
public class NotificationServlet extends HttpServlet {
    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {
        notificationService = (NotificationService) getServletContext().getAttribute("notificationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if (path.isEmpty()) {
                
            } else if (path.equals("/read")) {
                
            }
        } catch (RuntimeException e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            
        }
    }
}
