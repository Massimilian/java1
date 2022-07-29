<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 20.07.2022
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new password</title>
</head>
<body>
<h1>${requestScope.info}</h1>
<form action="/forget" method="post">
    <h2>Введите имя пользователя:</h2>
    <input type="text" name="name">
    <h2>Введите новый пароль:</h2>
    <input type="password" name="password">
    <h2>Повторите новый пароль:</h2>
    <input type="password" name="repeat">
    <input type="submit" value="Ввести данные">
</form>
</body>
</html>
