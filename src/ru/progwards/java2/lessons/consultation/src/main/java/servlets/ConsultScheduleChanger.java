package servlets;

import classes.Professor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class ConsultScheduleChanger extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ((Professor) req.getSession().getAttribute("professor")).setWorkTimes(new ArrayList<>());
        req.getSession().setAttribute("timedates", ((Professor) req.getSession().getAttribute("professor")).getWorkTimes());
        req.getSession().setAttribute("false", "");
        req.getRequestDispatcher("consrealchange.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String falta = "";
        int numOfDay = 0;
        LocalTime start = null;
        LocalTime finish = null;
        if (req.getParameter("dayofweek") == null) {
            falta = "Вы не выбрали день недели";
        } else {
            numOfDay = Integer.parseInt(req.getParameter("dayofweek"));
        }
        if (falta.equals("")) {
            if (req.getParameter("starttime").equals("")) {
                falta = "Вы не выбрали время начала занятий";
            } else {
                start = LocalTime.parse(req.getParameter("starttime") + ":00");
            }
        }
        if (falta.equals("")) {
            if (req.getParameter("finishtime").equals("")) {
                falta = "Вы не выбрали время окончания занятий";
            } else {
                finish = LocalTime.parse(req.getParameter("finishtime") + ":00");
                if (!start.isBefore(finish)) {
                    falta = "Введены некорректные значения времени. Попробуйте ещё раз";
                }
            }
        }
        if (falta.equals("")) {
            Professor professor = (Professor) req.getSession().getAttribute("professor");
            professor.setWorkTime(numOfDay, start, finish);
            falta = "Новое рабочее время добавлено.";
        }
        req.getSession().setAttribute("false", falta);
        req.getRequestDispatcher("consrealchange.jsp").forward(req, resp);
    }
}
