<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 12.07.2022
  Time: 7:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Crush!</title>
</head>
<body style="background-color: #FF0000">
<h1>Внимание! Зафиксирована попытка взлома!</h1>
<h1>${sessionScope.name}, Вас не должно быть тут!</h1>
<form action="/conexit">
    <input type="submit" value="Выйти">
</form>
</body>
</html>
