<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 11.04.2022
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fix consultation</title>
    <link href="css/write.css" rel="stylesheet" id="bootstrap-css">
</head>
<body>
<h1>Это страница записи на консультацию</h1><br>
<form action="newcons" method="post">
    <label for="username">Введите имя студента</label><br>
    <input type="text" name="username" id="username"><br>
    <label for="mentor">Введите имя преподавателя</label><br>
    <input type="text" name="mentor" id="mentor"><br>
    <label for="start">Введите время и дату начала консультации (записаться можно только более, чем за час до начала занятий!)</label><br>
    <input type="date" name="start" id="start"><br>
    <input type="time" name="time" id="time"><br>
    <label for="duration">Введите продолжительность консультации в минутах</label><br>
    <input type="number" name="duration" id="duration"><br>
    <label for="comment">Комментарии:</label><br>
    <input type="text" name="comment" id="comment"><br>
    <input type="submit" name="submit" value="submit"><br>
</form>
<form action="/consult">
    <button type="submit"> Вернуться в главное меню</button>
</form>

</body>
</html>
