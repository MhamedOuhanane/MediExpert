package com.mediexpert.filter;

import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.contains("/css/") || path.contains("/js/") || path.contains("/images/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if ( path.contains("/logout")) {
            if (!SESSIONUtil.isLogged(request)) {
                response.sendRedirect(request.getContextPath() + "/auth");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        if (path.startsWith("/auth")) {
            boolean canContinue = SESSIONUtil.protection(request, response, "");
            if (canContinue) {
                filterChain.doFilter(request, response);
            }
            return;
        } else {
            if (SESSIONUtil.getUser(request) == null) {
                response.sendRedirect(request.getContextPath() + "/auth");
                return;
            }
        }

        boolean canContinue = false;
        if (path.startsWith("/admin")) canContinue = SESSIONUtil.protection(request, response, "admin");
        else if (path.startsWith("/infirmier")) canContinue = SESSIONUtil.protection(request, response, "infirmier");
        else if (path.startsWith("/specialiste")) canContinue = SESSIONUtil.protection(request, response, "specialiste");
        else if (path.startsWith("/generaliste")) canContinue = SESSIONUtil.protection(request, response, "generaliste");
        else {
            filterChain.doFilter(request, response);
        }

        if (canContinue) {
            filterChain.doFilter(request, response);
        }
    }
}