package org.example.servlets.easy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8"); // устанавливаем кодировку выводимого сообщения
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("login.jsp").forward(req, resp); // обращаемся к странице .jsp и отправляем ответ дальше
    }
}
