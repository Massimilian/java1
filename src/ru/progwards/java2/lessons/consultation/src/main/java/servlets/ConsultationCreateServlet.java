package servlets;

import classes.Consultation;
import classes.Professor;
import classes.School;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;

public class ConsultationCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        session.setAttribute("falsed", "");
        String schedule = School.getSchool().getProfessorByName((String) session.getAttribute("professor")).getSchedule();
        session.setAttribute("professors_schedule", schedule);
        req.getRequestDispatcher("concreator.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        boolean hasData = false;
        Iterator<String> iterator = req.getParameterNames().asIterator();
        while (iterator.hasNext()) {
            hasData = iterator.next().equals("date");
            if (hasData) {
                break;
            }
        }
        if (!hasData) {
            this.doGet(req, resp);
        }
        session.setAttribute("consultDate", req.getParameter("date"));
        session.setAttribute("thema", req.getParameter("thema"));
        req.setAttribute("timeInfo", School.getSchool().getProfessorByName((String) session.getAttribute("professor")).getConsultationsScheduleByDay((String) session.getAttribute("consultDate")));
        session.setAttribute("falsed", "");
        req.getRequestDispatcher("concreator2page.jsp").forward(req, resp);
    }
}
