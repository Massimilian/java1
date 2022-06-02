<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 08.04.2022
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link href="css/reg.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>
<body>
<!-- Login Form -->
<form action="reged" method="post">
    <h1>Регистрация нового пользователя.</h1>
    <input type="text" id="login" class="fadeIn second" name="username" placeholder="login">
    <input type="text" id="password" class="fadeIn third" name="password" placeholder="password">
    <input type="submit" class="fadeIn fourth" value="Registrate">
</form>

</body>
</html>
