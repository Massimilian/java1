<%@ page import="org.example.servlets.consult2.classes.School" %>
<%@ page import="org.example.servlets.consult2.classes.Professor" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 01.06.2022
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create time</title>
</head>
<body>
<h2><span style="color: #FF0000">${sessionScope.falsed}</span></h2>
<h2>Выберите время начала консультации; продолжительность консультации составляет 15 минут.</h2>
<h2>Время, уже занятое другими студентами в выбранный Вами день:</h2>
<h2>${requestScope.timeInfo}</h2>
<h2>Менее, чем за два часа до времени начала консультации запись на консультацию не допускаются</h2>
<form action="/consultation2createtime" method="post">
    <input type="time" name="time"><br>
    <input type="submit" value="Выбрать время">
</form>
<form action="/consultation2create" method="get">
    <input type="submit" value="Вернуться">
</form>
</body>
</html>