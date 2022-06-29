<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.servlets.consult2.classes.TimeDatePeriod" %>
<%@ page import="org.example.servlets.consult2.classes.School" %>
<%@ page import="org.example.servlets.consult2.classes.Professor" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 22.06.2022
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change consultation dates and times</title>
</head>
<body>
<h2><span style="color: #FF0000"><%=request.getSession().getAttribute("false")%></span></h2>
<%
    ArrayList<TimeDatePeriod> timedates = (ArrayList<TimeDatePeriod>) session.getAttribute("timedates");
    for (int i = 0; i < timedates.size(); i++) { %>
<h1>
    <li><%=timedates.get(i).getNameOfDay()%>, <%=timedates.get(i).getTime().toString()%>.</li>
</h1>
<%}%>
<form action="/consschecha" method="post">
    <h1>Выберите день, когда Вы планируете проводить консультации:</h1>
    <h5>Понедельник.<input type="radio" name="dayofweek" value="1"/></h5>
    <h5>Вторник.<input type="radio" name="dayofweek" value="2"/></h5>
    <h5>Среда.<input type="radio" name="dayofweek" value="3"/></h5>
    <h5>Четверг.<input type="radio" name="dayofweek" value="4"/></h5>
    <h5>Пятница.<input type="radio" name="dayofweek" value="5"/></h5>
    <h5>Суббота.<input type="radio" name="dayofweek" value="6"/></h5>
    <h5>Воскресенье.<input type="radio" name="dayofweek" value="7"/></h5>
    <h1>Выберите время, когда Вы планируете начинать консультации:</h1>
    <input type="time" name="starttime">
    <h1>Выберите время окончания консультаций</h1>
    <input type="time" name="finishtime">
    <input type="submit" value="сохранить">
</form>
<form action="/conprofstart" method="get">
    <%
        School school = (School) session.getAttribute("school");
        Professor professor = (Professor) session.getAttribute("professor");
        school.renovateProfessorByName(professor.getName(), professor);
        School.close(school);
        session.setAttribute("school", School.getSchool());
    %>
    <input type="submit" value="Сохранить и вернуться в меню">
</form>
</body>
</html>
