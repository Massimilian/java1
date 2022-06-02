package org.example.servlets.consult2.servlets;

import org.example.servlets.consult2.classes.Consultation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ConsultationCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        session.setAttribute("false", "");
        req.getRequestDispatcher("concreator.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        if (Consultation.checkDate(req.getParameter("date"), session)) {
            session.setAttribute("consultDate", req.getParameter("date"));
            req.getRequestDispatcher("concreator2page.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("concreator.jsp").forward(req, resp);
        }
    }
}
