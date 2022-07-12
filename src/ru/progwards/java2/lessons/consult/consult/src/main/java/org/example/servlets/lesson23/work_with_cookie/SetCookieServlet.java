package org.example.servlets.lesson23.work_with_cookie;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CookieTwoServlet.getCookieByName(req);
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("cookieset.jsp").forward(req, resp);
    }
}
