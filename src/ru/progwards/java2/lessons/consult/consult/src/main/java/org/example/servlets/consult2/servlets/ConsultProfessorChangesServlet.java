package org.example.servlets.consult2.servlets;

import org.example.servlets.consult2.classes.Consultation;
import org.example.servlets.consult2.classes.Professor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ConsultProfessorChangesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("falsed", "");
        req.getRequestDispatcher("conprofchanges.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> names = req.getParameterNames();
        if (!names.hasMoreElements() || !names.nextElement().equals("status")) {
            req.getSession().setAttribute("falsed", "Необходимо выбрать критерий для изменения.");
            req.getRequestDispatcher("conprofchanges.jsp").forward(req, resp);
        } else {
            if (req.getParameter("status").equals("schedule")) {
                req.getRequestDispatcher("/conschansch").forward(req, resp);
            } else if (req.getParameter("status").equals("datas")){
                req.getRequestDispatcher("/conschand").forward(req, resp);
            } else {
                req.getSession().setAttribute("falsed", "Неизвестная серверная ошибка.");
                req.getRequestDispatcher("conprofchanges.jsp").forward(req, resp);
            }
        }
    }
}
