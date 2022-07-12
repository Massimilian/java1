<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 11.07.2022
  Time: 6:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success entry</title>
</head>
<body>
<h1>Hello, ${sessionScope.name}</h1>
<form action="/logout" method="get">
    <button type="submit">Logout</button>
</form>
</body>
</html>
