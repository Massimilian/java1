package servlets;

import classes.Chat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestartUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("info", null);
        req.getRequestDispatcher("newpassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Chat chat = Chat.getInstance();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String repeat = req.getParameter("repeat");
        String info = chat.renovateUser(name, password, repeat);
        req.setAttribute("info", info);
        if (info.equals("Данные успешно обновлены!")) {
            Chat.writeAll(chat);
            req.getRequestDispatcher("backdoor.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("newpassword.jsp").forward(req, resp);
        }
    }
}
