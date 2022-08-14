<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 13.08.2022
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login and password checker</title>
</head>
<body>
<form action="/less24" method="post">
  <h2>Введите логин:</h2>
  <input type="text" name="login" id="login">
  <h2> Введите пароль:</h2>
  <input type="password" name="password" id="password">
  <h2> Повторите пароль:</h2>
  <input type="password" name="repeat" id="repeat">
  <input type="submit" value="Отправить данные">
</form>
<script src="js/jquery-3.6.0.min.js"></script>
<script src="js/loginpassword.js"></script>

</body>
</html>
