package servlets;

import classes.Professor;
import classes.School;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConsultChangeData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = null;
        String password = null;
        if (!req.getParameter("name").equals("")) {
            name = req.getParameter("name");
        } else {
            req.getSession().setAttribute("falsed", "Вы не задали логин.");
            req.getRequestDispatcher("consprofchange.jsp").forward(req, resp);
        }
        if (!req.getParameter("password").equals("")) {
            password = req.getParameter("password");
            if (!password.equals(req.getParameter("repeat"))) {
                req.getSession().setAttribute("falsed", "Введённые пароли не совпадают. Попробуйте ещё раз");
                req.getRequestDispatcher("consprofchange.jsp").forward(req, resp);
            }
        } else {
            req.getSession().setAttribute("falsed", "Вы не задали пароль.");
            req.getRequestDispatcher("consprofchange.jsp").forward(req, resp);
        }
        req.getSession().setAttribute("falsed", "");
        Professor professor = (Professor) req.getSession().getAttribute("professor");
        professor.setName(name);
        professor.setPassword(password);
        professor.renovateConsultsProfessorName();
        School school = (School) req.getSession().getAttribute("school");
        school.renovateProfessorByName((String) req.getSession().getAttribute("name"), professor);
        School.close(school);
        req.getSession().setAttribute("school", school);
        req.getSession().setAttribute("professor", professor);
        req.getSession().setAttribute("name", professor.getName());
        req.getRequestDispatcher("/conprofstart").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("consprofchange.jsp").forward(req, resp);
    }
}
