package servlets;

import classes.Chat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StartAuthorizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        req.setAttribute("info", "");
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("start.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Chat chat = Chat.getInstance();
        String info = chat.checkUser(name, password);
        if (info == null) {
            req.getRequestDispatcher("/action").forward(req, resp);
        } else {
            req.setAttribute("info", info);
            req.getRequestDispatcher("start.jsp").forward(req, resp);
        }
    }
}
