<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 19.07.2022
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrate new user</title>
</head>
<body>
<h2>${requestScope.info}</h2>
<h2>Вы на странице регистрации нового пользователя</h2>
<form action="/newuser" method="post">
    <h3>Введите имя нового пользователя:</h3>
    <input type="text" name="name">
    <h3>Введите пароль:</h3>
    <input type="password" name="password">
    <h3>Повторите пароль:</h3>
    <input type="password" name="repeat">
    <h3>Введите цвет текста:</h3>
    <input type="color" name="color">
    <input type="submit" value="Закончить регистрацию">
</form>
<form action="/start" method="get">
    <input type="submit" value="Назад">
</form>
</body>
</html>
