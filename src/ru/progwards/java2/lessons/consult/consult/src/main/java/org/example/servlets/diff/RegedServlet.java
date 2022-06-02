package org.example.servlets.diff;

import org.example.UserList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegedServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserList.addNew(req.getParameter("username"), req.getParameter("password"));
        req.getRequestDispatcher("back.jsp").forward(req, resp);
    }
}
