package com.mediexpert.controller;

import com.mediexpert.model.Record;
import com.mediexpert.service.interfaces.ConsultationService;
import com.mediexpert.util.CSRFUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ConsultationServlet extends HttpServlet {
    private ConsultationService consultationService;

    @Override
    public void init() throws ServletException {
        consultationService = (ConsultationService) getServletContext().getAttribute("consultationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CSRFUtil.validationToken(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalide");
            return;
        }

        String method = req.getParameter("_method");
        if (method != null) {
            if ("PUT".equalsIgnoreCase(method)) {
                doPut(req, resp);
                return;
            } else if ("DELETE".equalsIgnoreCase(method)) {
                doDelete(req, resp);
                return;
            }
        }

        String path = req.getPathInfo() != null ? req.getPathInfo() : "/";
        try {
            if ("/add".equals(path)) {
                Record record = new Record();
                record.setId(req.getParameter(""));
            }
        } catch (RuntimeException e) {

        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
