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
<body style="color: #BDE72E">
<h1><span>Добро пожаловать, Administrator!</span></h1>
<h1>${sessionScope.info}</h1>
<h2><span>Выберите строку для работы.</span></h2>
<form action="/consultadmin" method="post">
    <h2>Ввести нового студента:</h2>
    <div class="newStudent">
        <h2>Имя:</h2>
        <input type="text" name="name">
        <h2>Пароль:</h2>
        <input type="text" name="password">
        <input type="submit" value="Ввести нового студента" name="add">
    </div>
    <h2>Удалить студента:</h2>
    <div class="delStudent">
        <h2>Имя:</h2>
        <input type="text" name="nameDel">
        <input type="submit" value="Удалить студента" name="delete">
    </div>
    <h2>Ввести нового преподавателя:</h2>
    <div class="newProfessor">
        <h2>Имя:</h2>
        <input type="text" name="nameProf">
        <h2>Пароль:</h2>
        <input type="text" name="passwordProf">
        <input type="submit" value="Ввести нового преподавателя" name="addProf">
    </div>
    <h2>Удалить преподавателя:</h2>
    <div class="delProfessor">
        <h2>Имя:</h2>
        <input type="text" name="nameProfDel">
        <input type="submit" value="Удалить преподавателя" name="deleteProf">
    </div>
</form>
<form action="/consultallschool" method="get">
    <input type="submit" value="Полный список">
</form>
<form action="/conexit">
    <input type="submit" value="Выход">
</form>
<script src="js/jquery-3.6.0.min.js"></script>
<script src="js/consultadminwa.js"></script>
</body>
</html>
