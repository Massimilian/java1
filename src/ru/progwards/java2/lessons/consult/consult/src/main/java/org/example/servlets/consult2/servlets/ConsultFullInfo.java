package org.example.servlets.consult2.servlets;

import org.example.servlets.consult2.classes.School;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class ConsultFullInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        School school = School.getSchool();
        req.getSession().setAttribute("allProfessors", school.getProfessorsNamesInString());
        req.getSession().setAttribute("allStudents", school.getStudentsNamesInString());
        req.getSession().setAttribute("finale", "Стереть всю информацию");
        req.getRequestDispatcher("consultFullInfo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println();
        if (req.getSession().getAttribute("finale").equals("Стереть всю информацию")) {
            req.getSession().setAttribute("finale", "Уничтожить школу - нажмите ещё раз.");
            req.getRequestDispatcher("consultFullInfo.jsp").forward(req, resp);
        } else {
            School school = School.getSchool();
            school.deleteAll();
            School.close(school);
            Enumeration<String> names = req.getSession().getAttributeNames();
            while(names.hasMoreElements()) {
                req.getSession().removeAttribute(names.nextElement());
            }
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
