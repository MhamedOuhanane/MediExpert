package com.mediexpert.util;

import com.mediexpert.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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

    public static boolean protection(HttpServletRequest request, HttpServletResponse response, String role) throws IOException {
        boolean isLogged = isLogged(request);

        if (!isLogged) {
            if (role.isEmpty()) {
                return true;
            } else {
                response.sendRedirect(request.getContextPath() + "/auth");
                return false;
            }
        } else {
            User user = getUser(request);
            String roleUser = user.getRole().getName();

            if (roleUser.equals(role)) {
                return true;
            }
            switch (roleUser) {
                case "infirmier" -> response.sendRedirect(request.getContextPath() + "/infirmier");
                case "specialiste" -> response.sendRedirect(request.getContextPath() + "/specialiste");
                case "generaliste" -> response.sendRedirect(request.getContextPath() + "/generaliste");
                case "admin" -> response.sendRedirect(request.getContextPath() + "/admin");
                default -> response.sendRedirect(request.getContextPath() + "/auth");
            }
            return false;
        }
    }

    public static void removeUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}