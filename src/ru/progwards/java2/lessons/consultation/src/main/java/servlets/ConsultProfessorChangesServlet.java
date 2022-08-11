package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class ConsultProfessorChangesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("sayhello", null);
        req.getSession().setAttribute("falsed", "");
        req.getRequestDispatcher("conprofchanges.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("status").equals("schedule")) {
            req.getRequestDispatcher("/conschansch").forward(req, resp);
        } else if (req.getParameter("status").equals("datas")) {
            req.getRequestDispatcher("/conschand").forward(req, resp);
        } else {
            req.getSession().setAttribute("falsed", "Неизвестная серверная ошибка.");
            req.getRequestDispatcher("conprofchanges.jsp").forward(req, resp);
        }
    }
}
