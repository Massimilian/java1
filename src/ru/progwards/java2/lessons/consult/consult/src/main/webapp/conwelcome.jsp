<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.servlets.consult2.classes.Professor" %>
<%@ page import="org.example.servlets.consult2.classes.School" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 01.06.2022
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h2><span style="color: #FF0000">${sessionScope.falsed}</span></h2>
<h1>Добро пожаловать на страницу консультаций, <%=session.getAttribute("name")%>!</h1>
<h2>Выберите преподавателя, у которого Вы хотите консультироваться</h2>
<c:set var="professors" value="${requestScope.professors}"></c:set>
<c:forEach var="point" items="${professors}">
    <c:set var="name" value="${point.name}"></c:set>
    <c:set var="schedule" value="${point.schedule}"></c:set>
    <ul>
        <form action = "/conswelcome" method="post">
            <input type="submit" value="${name}" name="${name}">
            ${schedule}
        </form>
    </ul>
</c:forEach>

<form action = "/condelete" method="get">
    <input type="submit" value="Удалить консультацию">
</form>
<form action = "/conexit">
    <input type="submit" value="Выйти">
</form>
</body>
</html>