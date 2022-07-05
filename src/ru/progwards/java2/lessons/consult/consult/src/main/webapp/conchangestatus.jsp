<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 19.06.2022
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Init</title>
</head>
<body>
<h2><span style="color: #FF0000"> ${sessionScope.falsed} </span></h2>
<form action="/constart" method="post">
  <h3>Выберите Ваш статус</h3>
  <h5>Студент<input type="radio" name="status" value="student" />Преподаватель<input type="radio" name="status" value="professor"/></h5>
  <h3>Введите имя:</h3>
  <input type="text" name="name"><br>
  <h3>Введите пароль:</h3>
  <input type="password" name="password"><br>
  <input type="submit" value="Войти">
</form>
<form action="index.jsp">
  <input type="submit" value="выход">
</form>
</body>
</html>
