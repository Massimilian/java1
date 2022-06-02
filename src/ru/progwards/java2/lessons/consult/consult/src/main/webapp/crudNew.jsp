<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 16.05.2022
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create New Task</title>
</head>
<body>
<form action="/crudNew" method="post">
    <h3>Введите имя задачи:</h3><br>
    <input type="text" name="taskName"><br>
    <h3>Введите текст задачи:</h3><br>
    <input type="text" name="taskValue"><br>
    <input type="submit" value="Создать">
</form>
</body>
</html>
