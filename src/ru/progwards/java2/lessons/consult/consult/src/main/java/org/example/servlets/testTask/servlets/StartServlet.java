package org.example.servlets.testTask.servlets;

import org.example.servlets.testTask.classes.InfoKeeper;
import org.example.servlets.testTask.classes.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class StartServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        InfoKeeper.initUsersAndPlaces("loginPassword.txt", true);
        InfoKeeper.initUsersAndPlaces("places.txt", false);
        super.init();
    }

    @Override
    public void destroy() {
        InfoKeeper.destroyUsersAndPlaces(InfoKeeper.users, true);
        InfoKeeper.destroyUsersAndPlaces(InfoKeeper.places, false);
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User newUser = new User(req.getParameter("username"), req.getParameter("password"), req.getParameter("picture"));
        if (InfoKeeper.users.contains(newUser)) {
            HttpSession session = req.getSession();
            session.setAttribute("userName", newUser.getName());
            req.getRequestDispatcher("jwelcome.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("jfail.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("jstart.jsp").forward(req, resp);
    }
}
