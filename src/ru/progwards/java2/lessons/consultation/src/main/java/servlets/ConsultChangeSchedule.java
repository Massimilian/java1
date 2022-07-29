package servlets;

import classes.Professor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ConsultChangeSchedule extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Professor professor = (Professor) req.getSession().getAttribute("professor");
        ArrayList<String> informations = new ArrayList<>();
        for (int i = 0; i < professor.getWorkTimes().size(); i++) {
            informations.add(professor.getWorkTimes().get(i).getNameOfDay() + ", " + professor.getWorkTimes().get(i).getTime().toString() + ".");
        }
        req.getSession().setAttribute("information", informations);
        req.getRequestDispatcher("consultschedulechange.jsp").forward(req, resp);
        req.getSession().setAttribute("information", null);
    }
}
