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
import java.time.LocalDateTime;

public class ConsultationCreateTimeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        School school = (School) session.getAttribute("school");
        if (Consultation.checkTime(req.getParameter("time"), session)) {
            session.setAttribute("consultTime", req.getParameter("time"));
            System.out.println(session.getAttribute("thema"));
            Professor current = School.getSchool().getProfessorByName((String) session.getAttribute("professor"));
            Consultation consultation = new Consultation(
                    LocalDateTime.of(Consultation.dateFromString((String) session.getAttribute("consultDate")), Consultation.timeFromString((String) session.getAttribute("consultTime"))),
                    (String) session.getAttribute("thema"), current.getName(), (String) session.getAttribute("name"));
            session.setAttribute("false", current.addConsultation(consultation));
            if (!session.getAttribute("false").equals("")) {
                session.setAttribute("consultDate", "");
                session.setAttribute("consultTime", "");
                req.getRequestDispatcher("concreator.jsp").forward(req, resp);
            } else {
                school.renovateProfessorByName(current.getName(), current);
                School.close(school);
                req.getRequestDispatcher("concreatedwell.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("concreator2page.jsp").forward(req, resp);
        }
    }
}
