package com.mediexpert.controller;

import com.mediexpert.model.Specialiste;
import com.mediexpert.model.User;
import com.mediexpert.service.interfaces.DemandeService;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
}
