package org.example.servlets.lesson23.work_with_cookie;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieOneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
//      более короткий вариант - но при нём значение берётся, строго говоря, не из Cookie
//      resp.addCookie(new Cookie("name", req.getParameter("word")));
//      req.setAttribute("name", req.getParameter("word"));
        Cookie cookie = new Cookie("name", req.getParameter("word"));
        cookie.setMaxAge(5);
        resp.addCookie(cookie);
        req.setAttribute("name", String.format("Значение cookie 'name' - %s.", cookie.getValue()));
        req.getRequestDispatcher("cookie_shower.jsp").forward(req, resp);
    }
}