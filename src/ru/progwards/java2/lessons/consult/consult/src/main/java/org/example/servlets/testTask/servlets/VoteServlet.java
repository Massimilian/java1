package org.example.servlets.testTask.servlets;

import org.example.servlets.testTask.classes.InfoKeeper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reaction = InfoKeeper.voter(InfoKeeper.current, req.getParameter("good"), req.getParameter("bad"), req.getSession().getAttribute("userName"));
        req.setAttribute("reaction", reaction);
        req.getRequestDispatcher("jvote.jsp").forward(req, resp);
    }
}
