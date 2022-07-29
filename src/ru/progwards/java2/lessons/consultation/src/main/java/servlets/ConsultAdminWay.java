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
        if (elements.size() == 2 && elements.contains("name") && elements.contains("password")) {
            this.doGet(req, resp);
        } else {
            School school = School.getSchool();
            if (elements.contains("add")) {
                name = req.getParameter("name");
                password = req.getParameter("password");
                if (name.equals("") || password.equals("")) {
                    req.getSession().setAttribute("info", "Некорректная информация. Введите ещё раз.");
                } else {
                    req.getSession().setAttribute("info", "Новый студент добавлен");
                    school.addStudent(new Student(name, password));
                }
            }
            if (elements.contains("delete")) {
                name = req.getParameter("name");
                if (name.equals("")) {
                    req.getSession().setAttribute("info", "Некорректная информация. Введите ещё раз.");
                } else {
                    req.getSession().setAttribute("info", String.format("Студент %s удалён", name));
                    school.deleteStudent(name);
                }
            }
            if (elements.contains("addProf")) {
                name = req.getParameter("name");
                password = req.getParameter("password");
                if (name.equals("")|| password.equals("")) {
                    req.getSession().setAttribute("info", "Некорректная информация. Введите ещё раз.");
                } else {
                    req.getSession().setAttribute("info", "Новый преподаватель добавлен");
                    school.addProfessor(new Professor(name, password));
                }
            }
            if (elements.contains("deleteProf")) {
                name = req.getParameter("name");
                if (name.equals("")) {
                    req.getSession().setAttribute("info", "Некорректная информация. Введите ещё раз.");
                } else {
                    req.getSession().setAttribute("info", String.format("Преподаватель %s удалён", name));
                    school.deleteProfessor(name);
                }
            }
            if (elements.contains("deleteStud")) {
                name = req.getParameter("name");
                if (name.equals("")) {
                    req.getSession().setAttribute("info", "Некорректная информация. Введите ещё раз.");
                } else {
                    req.getSession().setAttribute("info", String.format("Студент %s удалён", name));
                    school.deleteStudent(name);
                }
            }
            School.close(school);
            req.getRequestDispatcher("consultadminway.jsp").forward(req, resp);
        }
    }
}
