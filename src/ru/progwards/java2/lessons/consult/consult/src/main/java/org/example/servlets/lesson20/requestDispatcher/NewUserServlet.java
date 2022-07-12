package org.example.servlets.lesson20.requestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("maybeFalse", " ");
        req.getRequestDispatcher("newh20.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newUser = req.getParameter("newUser");
        String password = req.getParameter("password");
        String maybeFalse = this.checkNameAndPassword(newUser, password);
        req.setAttribute("maybeFalse", maybeFalse);
        if (maybeFalse.trim().isEmpty()) {
            LoginPassword.addNew(newUser, password);
            req.getRequestDispatcher("doneh20.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("newh20.jsp").forward(req, resp);
        }
    }

    private String checkNameAndPassword(String newUser, String password) {
        String result = " ";
        if (LoginPassword.users.containsKey(newUser)) {
            result += "Пользователь с таким ником существует. ";
        }
        if (newUser.trim().isEmpty()) {
            result += "Имя пользователя не может быть пустым. ";
        }
        if (password.trim().isEmpty()) {
            result += "Пароль не может быть пустым. ";
        }
        if (newUser.matches("[ ]*")) {
            result += "В имени пользователя не может быть пробелов. ";
        }
        if (password.length() < 8) {
            result += "Пароль слишком короткий. ";
        }
        if (password.matches("[\\D]+")) {
            result += "В пароле должны присутствовать цифры. ";
        }
        if (password.matches("[\\W]+")) {
            result += "В пароле должны присутствовать латинские буквы. ";
        }
        return result;
    }
}
