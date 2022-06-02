package org.example.servlets.diff;

import org.example.UserList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoggedDiffServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getParameter("username");
        Object password = req.getParameter("password");
        if (UserList.checkUser(user.toString(), password.toString())) {
            req.getRequestDispatcher("loggedDiff.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("false.jsp").forward(req, resp);
        }
    }
}
