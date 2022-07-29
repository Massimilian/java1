<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 19.07.2022
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorisation</title>
</head>
<body>
<h2>${requestScope.info}</h2>
<h2>Добро пожаловать в наш чат!</h2>
<form action="/start" method="post">
    <h3>Введите Ваше имя:</h3>
    <input type="text" name="name">
    <h3>Введите Ваш пароль:</h3>
    <input type="password" name="password">
    <input type="submit" value="Войти">
</form>
<form action="/newuser" method="get">
    <input type="submit" value="Зарегистрировать нового пользователя">
</form>
<form action="/forget" method="get">
    <input type="submit" value="Забыли пароль?">
</form>
</body>
</html>
