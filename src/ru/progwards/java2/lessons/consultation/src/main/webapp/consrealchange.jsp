<%@ page import="classes.School" %>
<%@ page import="classes.Professor" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 22.06.2022
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Change consultation dates and times</title>
</head>
<body>
<h2><span style="color: #FF0000">${sessionScope.falsed}</span></h2>
<c:forEach var="info" items="${information}">
    <h1>
        <li>${info}</li>
    </h1>
</c:forEach>
<form action="/consschecha" method="post">
    <h1>Выберите день, когда Вы планируете проводить консультации:</h1>
    <div class="dayOfWeek">
        <h5> Понедельник.<input type="radio" name="dayofweek" value="1"/></h5>
        <h5> Вторник.<input type="radio" name="dayofweek" value="2"/></h5>
        <h5> Среда.<input type="radio" name="dayofweek" value="3"/></h5>
        <h5> Четверг.<input type="radio" name="dayofweek" value="4"/></h5>
        <h5> Пятница.<input type="radio" name="dayofweek" value="5"/></h5>
        <h5> Суббота.<input type="radio" name="dayofweek" value="6"/></h5>
        <h5> Воскресенье.<input type="radio" name="dayofweek" value="7"/></h5>
    </div>
    <div class="startTime">
        <h1>Выберите время, когда Вы планируете начинать консультации:</h1>
        <input type="time" name="starttime" id="start">
    </div>
    <div class="finishTime">
        <h1>Выберите время окончания консультаций</h1>
        <input type="time" name="finishtime" id="finish">
    </div>
    <div class="save">
        <input type="submit" value="Сохранить">
    </div>
</form>
<form action="/conprofstart" method="get">
    <%
        School school = (School) session.getAttribute("school");
        Professor professor = (Professor) session.getAttribute("professor");
        school.renovateProfessorByName(professor.getName(), professor);
        School.close(school);
        session.setAttribute("school", School.getSchool());
    %>
    <input type="submit" value="Сохранить новое расписание и вернуться в меню">
</form>
<script src="js/jquery-3.6.0.min.js"></script>
<script src="js/consrealchange.js"></script>
</body>
</html>
