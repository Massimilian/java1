package servlets;

import classes.Professor;
import classes.School;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConsultationForProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("false", "");
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        School school = School.getSchool();
        Professor professor = school.getProfessorByName((String) req.getSession().getAttribute("name"));
        req.getSession().setAttribute("professor", professor);
        req.getRequestDispatcher("conprofessorpage.jsp").forward(req, resp);
    }
}
