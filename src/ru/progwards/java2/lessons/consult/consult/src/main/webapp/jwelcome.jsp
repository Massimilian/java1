<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 06.05.2022
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Добро пожаловать на страницу путешествий, <%= session.getAttribute("userName")%>!</h1>
<form action="/jsecondpage" method="get">
    <input type="submit" value="Продолжить">
</form>

</body>
</html>
