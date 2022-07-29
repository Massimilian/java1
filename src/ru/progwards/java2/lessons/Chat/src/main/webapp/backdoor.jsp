<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 20.07.2022
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success!</title>
</head>
<body>
<h1>${requestScope.info}</h1>
<form action="/start" method="get">
    <input type="submit" value="Вернуться назад">
</form>
</body>
</html>
