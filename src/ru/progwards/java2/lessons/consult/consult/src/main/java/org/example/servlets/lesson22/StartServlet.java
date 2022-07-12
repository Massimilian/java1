package org.example.servlets.lesson22;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Cookie cook = new Cookie("Name", "Vasia");
        cook.setMaxAge(3600); // жизненный цикл = 1 час
        cook.setMaxAge(-1); // жизненный цикл равен длительности работы браузера
        cook.setMaxAge(0); // cookie обнулена
        resp.addCookie(cook);
        resp.getWriter().write("<h1> Cookies. </h1>");
//        req.getRequestDispatcher("22test.jsp").forward(req, resp);
    }
}
