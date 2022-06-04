package org.example.servlets.consult2.servlets;

import org.example.servlets.consult2.classes.Consultation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ConsultationCreateTimeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        if (Consultation.checkTime(req.getParameter("time"), session)) {
            session.setAttribute("consultTime", req.getParameter("time"));
            System.out.println();
        } else {
            req.getRequestDispatcher("concreator2page.jsp").forward(req, resp);
        }
    }
}
