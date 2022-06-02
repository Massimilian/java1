<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 06.04.2022
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logged</title>
</head>
<body>
<h2>Hello, <%= request.getParameter("username") %>! Your password is <%= request.getParameter("password")%></h2>
<a href="/look">Посмотреть всех пользователей (пока что в консоли).</a><br>
<a href="/login">Вернуться на страницу инициализации.</a><br>
<a href="index.jsp">Вернуться на начальную страницу.</a>
</body>
</html>
