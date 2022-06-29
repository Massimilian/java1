<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 24.06.2022
  Time: 6:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administrator's page</title>
</head>
<body>
<h1><span style="color: #9ACD32">Добро пожаловать, Administrator!</span></h1>
<h1><span style="color: #BDE72E"><%=session.getAttribute("info")%></span> </h1>
<form action="/consultadmin" method="post">
    <h2><span style="color: #BDE72E">Ввести нового студента:</span></h2>
    <h2><span style="color: #BDE72E">Имя:</span></h2>
    <input type="text" name="name">
    <h2><span style="color: #BDE72E"> Пароль:</span></h2>
    <input type="text" name="password">
    <input type="submit" value="Ввести нового студента" name="add">
</form>
<form action="/consultadmin" method="post">
    <h2><span style="color: #BDE72E">Удалить студента:</span></h2>
    <h2><span style="color: #BDE72E">Имя:</span></h2>
    <input type="text" name="name">
    <input type="submit" value="Удалить студента" name="delete">
</form>
<form action="/consultadmin" method="post">
    <h2><span style="color: #BDE72E">Ввести нового преподавателя:</span></h2>
    <h2><span style="color: #BDE72E">Имя:</span></h2>
    <input type="text" name="name">
    <h2><span style="color: #BDE72E"> Пароль:</span></h2>
    <input type="text" name="password">
    <h2><span style="color: #BDE72E"> Фото:</span></h2>
    <input type="file" name="image">
    <input type="submit" value="Ввести нового преподавателя" name="addProf">
</form>
<form action="/consultadmin" method="post">
    <h2><span style="color: #BDE72E">Удалить преподавателя:</span></h2>
    <h2><span style="color: #BDE72E">Имя:</span></h2>
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
