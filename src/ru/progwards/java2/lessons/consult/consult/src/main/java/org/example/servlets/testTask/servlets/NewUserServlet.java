package org.example.servlets.testTask.servlets;

import org.example.servlets.testTask.classes.InfoKeeper;
import org.example.servlets.testTask.classes.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("maybeFalse", " ");
        req.getRequestDispatcher("jnew.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String newUser = req.getParameter("newUser");
        String password = req.getParameter("password");
        String picture = req.getParameter("picture");
        String maybeFalse = this.checkNameAndPassword(newUser, password);
        req.setAttribute("maybeFalse", maybeFalse);
        if (maybeFalse.trim().isEmpty()) {
            InfoKeeper.users.add(new User(newUser, password, picture));
            req.getRequestDispatcher("jdone.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("jnew.jsp").forward(req, resp);
        }
    }

    private String checkNameAndPassword(String newUser, String password) {
        String result = " ";
        if (InfoKeeper.hasUserName(newUser)) {
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
        return result;
    }
}
