package org.example.servlets.lesson23.work_with_cookie;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieTwoServlet extends HttpServlet {
    public static void getCookieByName(HttpServletRequest request) {
        Cookie result = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("name")) {
                result = cookies[i];
                break;
            }
        }
        request.setAttribute("name", result == null ? "Cookie отсутствует" : String.format("Значение cookie 'name' - %s.", result.getValue()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getCookieByName(req);
        req.getRequestDispatcher("cookie_shower.jsp").forward(req, resp);
    }
}
