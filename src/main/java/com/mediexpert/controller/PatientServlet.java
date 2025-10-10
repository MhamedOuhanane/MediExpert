package com.mediexpert.controller;

import com.mediexpert.service.interfaces.RecordService;
import com.mediexpert.util.CSRFUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/patients/*")
public class PatientServlet extends HttpServlet {
    private RecordService recordService;

    @Override
    public void init() {
        this.recordService = (RecordService) getServletContext().getAttribute("recordService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String csrfToken = CSRFUtil.getCsrfToken(req.getSession(false));
        if (csrfToken == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token pas exist");
            return;
        }
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();
        if (path.equals("/")) {
            var records = this.recordService.getAllRecord();
            req.setAttribute("records", records);
            req.setAttribute("currentRoute", "/patients");
            req.getRequestDispatcher(req.getContextPath() + "/pages/infirmier/patients-list.jsp").forward(req, resp);
            return;
        } else if (path.equals("/add")){
            req.setAttribute("csrfToken", csrfToken);
            req.setAttribute("patient", null);
            req.setAttribute("currentRoute", "/patients/add");
            req.getRequestDispatcher(req.getContextPath() + "/pages/infirmier/add-patient-form.jsp").forward(req, resp);
            return;
        }
    }
}
