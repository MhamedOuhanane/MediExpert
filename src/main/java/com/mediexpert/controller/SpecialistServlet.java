package com.mediexpert.controller;

import com.mediexpert.model.Specialiste;
import com.mediexpert.service.interfaces.SpecialisteService;
import com.mediexpert.util.SESSIONUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/specialist")
public class SpecialistServlet extends HttpServlet {
    private SpecialisteService specialisteService;

    @Override
    public void init() {
        this.specialisteService = (SpecialisteService) getServletContext().getAttribute("specialisteService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Specialiste specialiste = (Specialiste) SESSIONUtil.getUser(req);
        req.setAttribute("specialist", specialiste);
        req.setAttribute("currentRoute", "/specialist");
        req.getRequestDispatcher("pages/specialist/profile.jsp").forward(req, resp);
    }
}
