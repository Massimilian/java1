package org.example.servlets.consult;

import org.example.Consult;
import org.example.Consultation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewConsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        if (Consult.putNew(req.getParameter("username"),
                req.getParameter("mentor"), req.getParameter("start"),
                req.getParameter("time"), req.getParameter("duration"),
                req.getParameter("comment"))) {
            req.getRequestDispatcher("consultOk.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("consultFalse.jsp").forward(req, resp);
        }
    }
}
