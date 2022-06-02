<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 06.05.2022
  Time: 0:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New User</title>
</head>
<body>
<h1><%=request.getAttribute("maybeFalse")%></h1><br>
<form action="/jnew" method="post">
    <h3>Введите имя пользователя:</h3><br>
    <input type="text" name="newUser"><br>
    <h3>Введите пароль (не менее восьми цифр):</h3><br>
    <input type="password" name="password"><br>
    <h3>Введите ссылку на картинку:</h3><br>
    <input type="url" name="picture"><br>
    <input type="submit" value="Создать нового пользователя">
</form>
</body>
</html>
