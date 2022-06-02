<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 07.05.2022
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vote page</title>
</head>
<body>
<h1><%= request.getAttribute("reaction")%></h1>
<form action="/jsecondpage" method="get">
    <input type="submit" value="Продолжить">
</form>
</body>
</html>
