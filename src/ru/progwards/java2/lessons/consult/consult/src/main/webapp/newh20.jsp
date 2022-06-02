<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 02.05.2022
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New user</title>
</head>
<body>
<h1><%=request.getAttribute("maybeFalse")%></h1><br>
<form action="/newh20" method="post">
    <h3>Введите имя пользователя:</h3><br>
    <input type="text" name="newUser"><br>
    <h3>Введите пароль (не менее восьми цифр):</h3><br>
    <input type="password" name="password"><br>
    <input type="submit" value="Create new User">
</form>
</body>
</html>
