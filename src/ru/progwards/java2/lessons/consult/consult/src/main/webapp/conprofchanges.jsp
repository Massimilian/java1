<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 22.06.2022
  Time: 8:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Change professor's characteristics</title>
</head>
<body>
<span style="color: #FF0000"><h1>${sessionScope.falsed}</h1></span>
<h1>Вы на странице изменений учётной записи. Что вы хотите сделать?</h1>
<form action="/professorchanges" method="post">
<h5>Изменить расписание<input type="radio" name="status" value="schedule" />
    Изменить имя и пароль<input type="radio" name="status" value="datas"/></h5>
    <input type="submit" value="Принять">
</form>
</body>
</html>
