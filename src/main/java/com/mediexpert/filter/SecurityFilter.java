package com.mediexpert.filter;

import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.contains("/css/") || path.contains("/js/") || path.contains("/images/") || path.contains("/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/connection")) {
            SESSIONUtil.protection(request, response, "");
            filterChain.doFilter(request, response);
            return;
        }


        if (path.startsWith("/admin")) SESSIONUtil.protection(request, response, "admin");
        else if (path.startsWith("/infirmier")) SESSIONUtil.protection(request, response, "infirmier");
        else if (path.startsWith("/specialiste")) SESSIONUtil.protection(request, response, "specialiste");
        else if (path.startsWith("/generaliste")) SESSIONUtil.protection(request, response, "generaliste");
        else {
            filterChain.doFilter(request, response);
            return;
        };

        filterChain.doFilter(request, response);
    }
}
