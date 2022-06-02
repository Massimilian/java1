<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 05.04.2022
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logged</title>
</head>
<body>
<h2>Hello, <%= request.getParameter("username") %>! Your password is <%= request.getParameter("password")%></h2>
<a href="/time">Вернуться</a>
</body>
</html>
