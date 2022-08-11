package servlets;

import classes.Professor;
import classes.School;
import classes.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ConsultAdminWay extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("info", "");
        req.getRequestDispatcher("consultadminway.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Enumeration<String> parameters = req.getParameterNames();
        ArrayList<String> elements = new ArrayList<>();
        String name = "";
        String password = "";
        while (parameters.hasMoreElements()) {
            elements.add(parameters.nextElement());
        }
//        if (elements.size() == 2 && elements.contains("name") && elements.contains("password")) {
//            this.doGet(req, resp);
//        } else {
        System.out.println(req.getParameter("name"));
        System.out.println(req.getParameter("password"));
        School school = School.getSchool();
        if (elements.contains("add")) {
            name = req.getParameter("name");
            password = req.getParameter("password");
            req.getSession().setAttribute("info", school.addPerson(new Student(name, password)) ?
                    "Новый студент добавлен" : "Студент с такими именем и паролем уже существует.");
        }
        if (elements.contains("delete")) {
            name = req.getParameter("nameDel");
            req.getSession().setAttribute("info", school.deletePerson(name, false) ?
                    String.format("Студент %s удалён", name) : String.format("Студент %s не найден", name));
        }
        if (elements.contains("addProf")) {
            name = req.getParameter("nameProf");
            password = req.getParameter("passwordProf");
            req.getSession().setAttribute("info", school.addPerson(new Professor(name, password)) ?
                    "Новый преподаватель добавлен" : "Преподаватель с такими именем и паролем уже существует.");
        }
        if (elements.contains("deleteProf")) {
            name = req.getParameter("nameProfDel");
            req.getSession().setAttribute("info", school.deletePerson(name, true) ?
                    String.format("Преподаватель %s удалён", name) : String.format("Преподаватель %s не найден", name));
        }
        School.close(school);
        req.getRequestDispatcher("consultadminway.jsp").forward(req, resp);
    }
}
