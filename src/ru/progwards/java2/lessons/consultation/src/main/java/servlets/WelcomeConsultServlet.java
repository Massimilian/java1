package servlets;

import classes.School;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class WelcomeConsultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("false", "");
        req.getRequestDispatcher("conwelcome.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getSession().getAttribute("false") == null) {
            this.doGet(req, resp);
        } else {
            Enumeration<String> enumeration = req.getParameterNames();
            School school = (School) req.getSession().getAttribute("school");
            boolean hasProf = false;
            while (enumeration.hasMoreElements()) {
                String temp = enumeration.nextElement();
                if (school.getProfessorsNames().contains(temp)) {
                    hasProf = true;
                    req.getSession().setAttribute("professor", temp);
                    break;
                }
            }
            if (hasProf) {
                req.getSession().setAttribute("false", "");
                req.getRequestDispatcher("/consultation2create").forward(req, resp);
            } else {
                req.getSession().setAttribute("false", "Непредвиденная ошибка: пожалуйста, выберите другого преподавателя.");
                req.getRequestDispatcher("conwelcome.jsp").forward(req, resp);
            }
        }
    }
}