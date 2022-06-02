package org.example.servlets.consult2.servlets;

import org.example.servlets.consult2.classes.School;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeConsultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        School school = new School();
        req.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("school", school);
        req.getRequestDispatcher("conwelcome.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

    }
}
