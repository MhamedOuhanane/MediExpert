package com.mediexpert.controller;

import com.mediexpert.model.Generaliste;
import com.mediexpert.model.Infirmier;
import com.mediexpert.model.Specialiste;
import com.mediexpert.model.User;
import com.mediexpert.service.interfaces.RoleService;
import com.mediexpert.service.interfaces.UserService;
import com.mediexpert.util.CSRFUtil;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        String csrfToken = CSRFUtil.getCsrfToken(request.getSession(true));
        request.setAttribute("csrfToken", csrfToken);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (CSRFUtil.validationToken(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalide");
            return;
        }
        try {
            User loggedUser = (User) this.userService.login(email, password);
            String url;
            switch (loggedUser.getRole().getName()) {
                case "infirmier":
                    Infirmier infirmier = (Infirmier) loggedUser;
                    SESSIONUtil.setUser(request, infirmier);
                    url = "infirmier/dashboard.jsp";
                    break;

                case "specialiste":
                    Specialiste specialiste = (Specialiste) loggedUser;
                    SESSIONUtil.setUser(request, specialiste);
                    url = "specialiste/dashboard.jsp";
                    break;

                default:
                    Generaliste generaliste = (Generaliste) loggedUser;
                    SESSIONUtil.setUser(request, generaliste);
                    url = "generaliste/dashboard.jsp";
                    break;
            }

            response.sendRedirect(url);

        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
