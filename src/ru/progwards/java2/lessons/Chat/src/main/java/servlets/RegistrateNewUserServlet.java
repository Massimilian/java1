package servlets;

import classes.Chat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrateNewUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("regnew.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String repeat = req.getParameter("repeat");
        String color = req.getParameter("color");
        Chat chat = Chat.getInstance();
        String result = chat.addUser(name, password, repeat, color, false);
        req.setAttribute("info", result);
        if (result.equals("Новый пользователь добавлен!")) {
            Chat.writeAll(chat);
            req.getRequestDispatcher("backdoor.jsp").forward(req, resp);
        } else {
            this.doGet(req, resp);
        }
    }
}
