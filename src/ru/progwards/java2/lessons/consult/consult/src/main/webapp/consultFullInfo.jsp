<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 28.06.2022
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Full Info deleter.</title>
    <style>
        <c:set var="temp" value="Уничтожить школу - нажмите ещё раз."></c:set>
        <c:if test="${finale == temp}">
        body {
            background-color: #FF0000;
        }
        </c:if>
    </style>
</head>
<body span style="color: #BDE72E">
<h2>${sessionScope.allProfessors}</h2>
<h2>${sessionScope.allStudents}</h2>
<form action="/consultallschool" method="post">
    <input type="submit" value="${sessionScope.finale}">
</form>
<form action="/consultadmin">
    <input type="submit" value="Назад">
</form>
</body>
</html>
