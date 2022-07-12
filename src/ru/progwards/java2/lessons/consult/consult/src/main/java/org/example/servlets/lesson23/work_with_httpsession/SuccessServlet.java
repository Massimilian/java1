package org.example.servlets.lesson23.work_with_httpsession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!req.getSession().isNew()) {
            req.getRequestDispatcher("successh23.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/servletlogin").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CheckServlet.checker(req)) {
            req.getRequestDispatcher("/servletlogin").forward(req, resp);
        } else {
            this.doPost(req, resp);
        }
    }
}
