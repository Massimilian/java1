package org.example.servlets.testTask.servlets;

import org.example.servlets.testTask.classes.InfoKeeper;
import org.example.servlets.testTask.classes.Place;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewPlaceCreatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("jcreate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String header = req.getParameter("header");
        String body = req.getParameter("body");
        String fotoLocation = req.getParameter("location");
        String author = (String) req.getSession().getAttribute("userName");
        Place place = new Place(header, body, fotoLocation, author);
        InfoKeeper.places.add(place);
        req.getRequestDispatcher("/jsecondpage").forward(req, resp);

    }
}
