package org.example.servlets.consult2.servlets;

import org.example.servlets.consult2.classes.School;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConsultationStart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getSession().getAttribute("false") == null) {
            req.getSession().setAttribute("false", "");
        }
        req.getSession().setAttribute("isForDoGet", "true");
        req.getRequestDispatcher("conchangestatus.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String forDoGet = (String) req.getSession().getAttribute("isForDoGet");
        if (forDoGet != null) {
            req.getSession().setAttribute("isForDoGet", null);
            String status = req.getParameter("status");
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            School school = School.getSchool();
            req.getSession().setAttribute("school", school);
            String falta;
            if (status == null) {
                if (name.equals("admin") && password.equals("admin")) {
                    req.getRequestDispatcher("/consultadmin").forward(req, resp);
                } else {
                    falta = "Вы не указали свой статус. Попробуйте ещё раз.";
                    req.getSession().setAttribute("false", falta);
                    req.getRequestDispatcher("/constart").forward(req, resp);
                }
            } else if (status.equals("student")) {
                falta = school.isCorrect(name, password, true);
                if (falta.equals("")) {
                    req.getSession().setAttribute("false", null);
                    req.getSession().setAttribute("name", name);
                    req.getRequestDispatcher("/conswelcome").forward(req, resp);
                } else {
                    req.getSession().setAttribute("false", falta);
                    req.getRequestDispatcher("/constart").forward(req, resp);
                }
            } else if (status.equals("professor")) {
                falta = school.isCorrect(name, password, false);
                if (falta.equals("")) {
                    req.getSession().setAttribute("false", null);
                    req.getSession().setAttribute("name", name);
                    req.getRequestDispatcher("/conprofstart").forward(req, resp);
                } else {
                    req.getSession().setAttribute("false", falta);
                    req.setAttribute("oneMore", "true");
                    req.getRequestDispatcher("/constart").forward(req, resp);
                }
            }
        } else {
            this.doGet(req, resp);
        }
    }
}
