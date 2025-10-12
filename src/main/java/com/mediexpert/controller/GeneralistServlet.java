package com.mediexpert.controller;

import com.mediexpert.model.Generaliste;
import com.mediexpert.service.interfaces.GeneralisteService;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/generalist")
public class GeneralistServlet extends HttpServlet {
    private GeneralisteService generalisteService;

    @Override
    public void init() {
        this.generalisteService = (GeneralisteService) getServletContext().getAttribute("generalisteService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Generaliste generalist = (Generaliste) SESSIONUtil.getUser(req);
        req.setAttribute("generalist", generalist);
        req.setAttribute("currentRoute", "/generalist");
        req.getRequestDispatcher( "pages/generalist/profile.jsp").forward(req, resp);
    }
}
