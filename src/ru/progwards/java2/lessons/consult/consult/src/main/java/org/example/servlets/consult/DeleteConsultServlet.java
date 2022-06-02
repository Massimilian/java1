package org.example.servlets.consult;

import org.example.Consult;
import org.example.Consultation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteConsultServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("delete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String mentor = req.getParameter("mentor");
        long time = Consult.prepareStart(req.getParameter("start"), req.getParameter("time"));
        Consultation consultation = new Consultation(mentor, time, 0L, "", "");
        if (Consult.delete(consultation)) {
            req.getRequestDispatcher("consultOk.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("consultDeleteFalse.jsp").forward(req, resp);
        }
    }
}
