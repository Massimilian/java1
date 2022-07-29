<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 28.05.2022
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create date</title>
</head>
<body>
<h2><span style="color: #FF0000">${sessionScope.falsed}</span></h2>
<form action="/consultation2create" method="post">
    <script>alert("Не забудьте, что ${sessionScope.professor} имеет следующее расписание: ${sessionScope.professors_schedule}")</script>
    <h3>Введите Ваши вопросы для консультации:</h3>
    <input type="text" name="thema">
    <h3>Выберите дату начала занятий</h3><br>
    <input type="date" name="date"><br>
    <input type="submit" value="Выбрать дату">
</form>
<form action="/conswelcome">
    <input type="submit" value="Изменить выбор преподавателя">
</form>
<form action="/conexit">
    <input type="submit" value="Выйти">
</form>
</body>
</html>
