package org.example.servlets.testTask.servlets;

import org.example.servlets.testTask.classes.InfoKeeper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecondPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InfoKeeper.current = null;
        req.setAttribute("places", InfoKeeper.places);
        req.getRequestDispatcher("jsecond.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        InfoKeeper.current = InfoKeeper.getPlace(req);
        InfoKeeper.current.incrLooks();
        req.setAttribute("head", InfoKeeper.current.getHeader());
        req.setAttribute("body", InfoKeeper.current.getBody());
        req.setAttribute("location", InfoKeeper.current.getFotoLocation());
        req.setAttribute("author", InfoKeeper.current.getAuthor());
        req.setAttribute("likes", InfoKeeper.current.getLikes());
        req.setAttribute("date", InfoKeeper.current.getDate());
        req.setAttribute("looks", InfoKeeper.current.getNumberOfLooks());
        req.setAttribute("picture", InfoKeeper.getPictureByAuthor(InfoKeeper.current.getAuthor()));
        req.getRequestDispatcher("jshow.jsp").forward(req, resp);
    }
}
