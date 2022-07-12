package org.example.servlets.lesson20.requestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    static {
        LoginPassword.addNew("user", "pass");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("logh20.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("username");
        String password = req.getParameter("password");
        if (LoginPassword.users.containsKey(nickname) && LoginPassword.users.get(nickname).equals(password)) {
            req.getRequestDispatcher("/successh20").forward(req, resp);
        } else {
            req.getRequestDispatcher("/failh20").forward(req, resp);
        }
    }
}
