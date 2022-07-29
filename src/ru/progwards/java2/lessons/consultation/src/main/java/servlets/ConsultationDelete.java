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
import java.util.Enumeration;

public class ConsultationDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        School school = (School) req.getSession().getAttribute("school");
        ArrayList<Consultation> consults = school.prepareConsultationListByStudentName((String) req.getSession().getAttribute("name"));
        req.getSession().setAttribute("consultations", consults.size() == 0? null : consults);
        req.getRequestDispatcher("consultdelete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Enumeration<String> enumeration = req.getParameterNames();
        if (enumeration.hasMoreElements()) {
            String dateTimeName = enumeration.nextElement();
            ArrayList<Consultation> consults = (ArrayList<Consultation>) req.getSession().getAttribute("consultations");
            School school = (School) req.getSession().getAttribute("school");
            Consultation current = Consultation.findByString(consults, dateTimeName);
            Professor professor = school.getProfessorByName(Consultation.getProfessorFromString(dateTimeName));
            school.renovateProfessorInfo(professor, current);
            req.getSession().setAttribute("school", School.getSchool());
            req.getSession().setAttribute("consultations", null);
            req.getRequestDispatcher("consultdeletedwell.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("false", "Непредвиденная ошибка. Пожалуйста, попробуйте ещё раз");
            req.getRequestDispatcher("conwelcome.jsp").forward(req, resp);
        }
    }
}
