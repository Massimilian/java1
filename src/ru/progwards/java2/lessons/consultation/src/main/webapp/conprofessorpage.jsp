<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 22.06.2022
  Time: 7:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Professor's page</title>
</head>
<body>
<c:if test="${sessionScope.sayhello != null}">
    <script>alert("Добро пожаловать домой, ${sessionScope.name}!")</script>
</c:if>
<h1>Здравствуйте, ${sessionScope.name}! Ваши предстоящие консультации:</h1>
<ul>
    <c:forEach var="info" items="${sessionScope.information}">
        <li><h1>${info}</h1></li>
    </c:forEach>
</ul>
<form action="/professorchanges" method="get">
    <input type="submit" value="Изменения в учётную запись.">
</form>
<form action="/conexit">
    <input type="submit" value="Выход">
</form>
</body>
</html>
