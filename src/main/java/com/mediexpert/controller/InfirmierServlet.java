package com.mediexpert.controller;

import com.mediexpert.model.Infirmier;
import com.mediexpert.service.interfaces.InfirmierService;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/infirmier")
public class InfirmierServlet extends HttpServlet {
    private InfirmierService infirmierService;

    @Override
    public void init() {
        this.infirmierService = (InfirmierService) getServletContext().getAttribute("infirmierService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Infirmier infirmier = (Infirmier) SESSIONUtil.getUser(req);
        req.setAttribute("infirmier", infirmier);
        req.setAttribute("currentRoute", "/infirmier");
        req.getRequestDispatcher( "pages/infirmier/profile.jsp").forward(req, resp);
    }
}
