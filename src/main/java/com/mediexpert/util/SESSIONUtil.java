package com.mediexpert.util;

import com.mediexpert.model.Generaliste;
import com.mediexpert.model.Infirmier;
import com.mediexpert.model.Specialiste;
import com.mediexpert.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

public class SESSIONUtil {
    private static final String USER_SESSION_KEY = "user";

    public static void setUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(USER_SESSION_KEY, user);
    }

    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? (User) session.getAttribute(USER_SESSION_KEY) : null;
    }

    public static boolean isLogged(HttpServletRequest request) {
        return getUser(request) != null;
    }

    public static void protection(HttpServletRequest request, HttpServletResponse response, String role) throws IOException {
        boolean isLogged = isLogged(request);
        if (!isLogged) {
            if (role.isEmpty()) return;
            else response.sendRedirect("login.jsp");
        } else {
            User user = getUser(request);
            String roleUser = user.getRole().getName();
            if (Objects.equals(roleUser, role)) return;
            switch (roleUser) {
                case "infirmier" -> response.sendRedirect("infirmier/dashboard.jsp");
                case "specialiste" -> response.sendRedirect("specialiste/dashboard.jsp");
                case "generaliste" -> response.sendRedirect("generaliste/dashboard.jsp");
                case "admin" -> response.sendRedirect("admin/dashboard.jsp");
                default -> response.sendRedirect("login.jsp");
            }
            return;
        }
    }

    public static void removeUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(USER_SESSION_KEY);
            session.invalidate();
        }
    }
}
