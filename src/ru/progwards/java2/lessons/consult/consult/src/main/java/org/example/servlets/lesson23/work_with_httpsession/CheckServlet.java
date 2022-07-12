package org.example.servlets.lesson23.work_with_httpsession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class CheckServlet extends HttpServlet {
    public static boolean checker(HttpServletRequest request) {
        boolean hasParameter = false;
        Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            if (name.equals("name") && request.getParameter("name").equals("admin")) {
                hasParameter = true;
                break;
            }
        }
        return hasParameter;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("login").equals("admin") && req.getParameter("password").equals("1234")) {
            req.getSession().setAttribute("name", req.getParameter("login"));
            req.getSession().setMaxInactiveInterval(20);
            getServletContext().getRequestDispatcher("/success").forward(req, resp);
        } else {
            req.getRequestDispatcher("/servletlogin").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checker(req)) {
            req.getRequestDispatcher("/servletlogin").forward(req, resp);
        } else {
            this.doPost(req, resp);
        }
    }
}
