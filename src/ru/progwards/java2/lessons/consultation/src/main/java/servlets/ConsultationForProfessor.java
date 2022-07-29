package servlets;

import classes.Consultation;
import classes.Professor;
import classes.School;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ConsultationForProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("falsed", "");
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        School school = School.getSchool();
        Professor professor = school.getProfessorByName((String) req.getSession().getAttribute("name"));
        req.getSession().setAttribute("professor", professor);
        ArrayList<Consultation> consultations = professor.getConsults();
        ArrayList<String> informations = new ArrayList<>();
        for (int i = 0; i < consultations.size(); i++) {
            informations.add(String.format("%s, в %s; тема консультации - '%s', студент - %s.",
                    consultations.get(i).getStart().toLocalDate(), consultations.get(i).getStart().toLocalTime(),
                    consultations.get(i).getThema(), consultations.get(i).getStudent()));
        }
        req.getSession().setAttribute("information", informations);
        req.getRequestDispatcher("conprofessorpage.jsp").forward(req, resp);
    }
}
