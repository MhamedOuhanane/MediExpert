package com.mediexpert.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.UUID;

public class CSRFUtil {
    private static final String CSRF_TOKEN = "csrfToken";

    public static String generatedToken(HttpSession session) {
        String token = (String) session.getAttribute(CSRF_TOKEN);
        if (token == null) {
            token = UUID.randomUUID().toString();
            session.setAttribute(CSRF_TOKEN, token);
        }
        return token;
    }

    public static String getCsrfToken(HttpSession session) {
        if (session == null) return null;
        Object token = session.getAttribute(CSRF_TOKEN);
        return token != null ? token.toString() : null;
    }

    public static boolean validationToken(HttpServletRequest request) {
        String sessionToken = getCsrfToken(request.getSession(false));
        String formToken = request.getParameter(CSRF_TOKEN);
        return sessionToken != null && sessionToken.equals(formToken);
    }
}
