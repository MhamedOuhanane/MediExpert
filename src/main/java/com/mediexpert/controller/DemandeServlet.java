package com.mediexpert.controller;

import com.mediexpert.enums.DemandeStatut;
import com.mediexpert.model.Demande;
import com.mediexpert.model.Specialiste;
import com.mediexpert.model.User;
import com.mediexpert.service.interfaces.DemandeService;
import com.mediexpert.util.CSRFUtil;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebServlet("/demandes/*")
public class DemandeServlet extends HttpServlet {
    private DemandeService demandeService;

    @Override
    public void init() throws ServletException {
        this.demandeService = (DemandeService) getServletContext().getAttribute("demandeService");
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
}
