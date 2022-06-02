<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 11.04.2022
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete consultation</title>
    <link href="css/delete.css" rel="stylesheet" id="bootstrap-css">
</head>
<body>
<h1>Это страница удаления консультаций</h1>
<form action="delete" method="post">
    <label for="mentor">Введите имя преподавателя, у которого записана консультация</label><br>
    <input type="text" name="mentor" id="mentor"><br>
    <label for="start">Введите время и дату начала консультации</label><br>
    <input type="date" name="start" id="start"><br>
    <input type="time" name="time" id="time"><br>
    <input type="submit" name="submit" value="submit"><br>
</form>

<form action="/consult">
    <button type="submit"> Вернуться в главное меню</button>
</form>
</body>
</html>
