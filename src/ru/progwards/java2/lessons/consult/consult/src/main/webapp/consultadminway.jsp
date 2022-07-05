<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 24.06.2022
  Time: 6:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Administrator's page</title>
</head>
<body span style=" color: #BDE72E">
<h1><span style="color: #9ACD32">Добро пожаловать, Administrator!</span></h1>
<h1>${sessionScope.info}</h1>
<form action="/consultadmin" method="post">
    <h2>Ввести нового студента:</h2>
    <h2>Имя:</h2>
    <input type="text" name="name">
    <h2>Пароль:</h2>
    <input type="text" name="password">
    <input type="submit" value="Ввести нового студента" name="add">
</form>
<form action="/consultadmin" method="post">
    <h2>Удалить студента:</h2>
    <h2>Имя:</h2>
    <input type="text" name="name">
    <input type="submit" value="Удалить студента" name="delete">
</form>
<form action="/consultadmin" method="post">
    <h2>Ввести нового преподавателя:</h2>
    <h2>Имя:</h2>
    <input type="text" name="name">
    <h2>Пароль:</span></h2>
    <input type="text" name="password">
    <!--  <h2><span style="color: #BDE72E"> Фото:</span></h2>
      <input type="file" name="image"> -->
    <input type="submit" value="Ввести нового преподавателя" name="addProf">
</form>
<form action="/consultadmin" method="post">
    <h2>Удалить преподавателя:</h2>
    <h2>Имя:</h2>
    <input type="text" name="name">
    <input type="submit" value="Удалить студента" name="deleteProf">
</form>
<form action="/consultallschool" method="get">
    <input type="submit" value="Полный список">
</form>
<form action="index.jsp">
    <input type="submit" value="Выход">
</form>
</body>
</html>
