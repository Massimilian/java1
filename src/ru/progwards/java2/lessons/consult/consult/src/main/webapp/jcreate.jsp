<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 07.05.2022
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creator</title>
</head>
<body>
<form action="/jnewplace" method="post">
    <h3>Введите название вашего места:</h3>
    <input type="text" name="header"><br>
    <h3>Расскажите об этом месте подробнее:</h3>
    <input type="text" name="body"><br>
    <h3>Введите ссылку на картинку:</h3>
    <input type="url" name="location">
    <input type="submit" value="Новое воспоминание">
</form>
</body>
</html>
