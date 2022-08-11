<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 19.06.2022
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Well done!</title>
</head>
<body>
<h2>Консультация добавлена. Не забудьте про Вашу консультацию, которая произойдёт ${sessionScope.consultDate} в ${sessionScope.consultTime}.</h2>
<h2>Ваш преподаватель - ${sessionScope.professor}.</h2>
<form action="/conswelcome" method="get">
    <input type="submit" value="Вернуться (SPACE)">
</form>
<script src="js/jquery-3.6.0.min.js"></script>
<script src="js/concreatedordeletedwell.js"></script>
</body>
</html>
