<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 22.06.2022
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Change schedule</title>
</head>
<body>
<h1>Ваше текущее расписание:</h1>
<ul>
    <c:forEach var="info" items="${sessionScope.information}">
        <li><h1>${info}</h1></li>
    </c:forEach>
</ul>
<form action="/consschecha" method="get" class="forward">
    <input type="submit" value="Заменить расписание (ENTER)">
</form>
<form action="/conprofstart" method="post" class="back">
    <input type="submit" value="Вернуться (SPACE)">
</form>
<script src="js/jquery-3.6.0.min.js"></script>
<script src="js/consultschedulechange.js"></script>
</body>
</html>
