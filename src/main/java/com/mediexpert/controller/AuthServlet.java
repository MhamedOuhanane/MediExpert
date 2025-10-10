package com.mediexpert.controller;

import com.mediexpert.model.*;
import com.mediexpert.service.interfaces.RoleService;
import com.mediexpert.service.interfaces.UserService;
import com.mediexpert.util.CSRFUtil;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/connection")
public class AuthServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SESSIONUtil.protection(request, response, "");
        String csrfToken = CSRFUtil.generatedToken(request.getSession(true));
        Object erreur = request.getAttribute("errorMessage");
        if (erreur != null) request.removeAttribute("errorMessage");
        request.setAttribute("csrfToken", csrfToken);
        request.getRequestDispatcher(request.getContextPath() + "/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (!CSRFUtil.validationToken(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalide");
            return;
        }
        try {
            User loggedUser = (User) this.userService.login(email, password);
            String url;
            switch (loggedUser.getRole().getName()) {
                case "admin":
                    Admin admin = (Admin) loggedUser;
                    SESSIONUtil.setUser(request, admin);
                    url = "/admin";
                    break;
                case "infirmier":
                    Infirmier infirmier = (Infirmier) loggedUser;
                    SESSIONUtil.setUser(request, infirmier);
                    url = "/infirmier";
                    break;

                case "specialiste":
                    Specialiste specialiste = (Specialiste) loggedUser;
                    SESSIONUtil.setUser(request, specialiste);
                    url = "/specialiste";
                    break;

                default:
                    Generaliste generaliste = (Generaliste) loggedUser;
                    SESSIONUtil.setUser(request, generaliste);
                    url = "/generaliste";
                    break;
            }

            response.sendRedirect(url);

        } catch (RuntimeException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
