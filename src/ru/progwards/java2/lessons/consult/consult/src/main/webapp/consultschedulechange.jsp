<%@ page import="org.example.servlets.consult2.classes.Professor" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 22.06.2022
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change schedule</title>
</head>
<body>
<h1>Ваше текущее расписание:</h1>
<ul>
    <% Professor professor = (Professor) session.getAttribute("professor");
        for (int i = 0; i < professor.getWorkTimes().size(); i++) {
    %>
    <li>
        <h1><%=professor.getWorkTimes().get(i).getNameOfDay()%>
            , <%=professor.getWorkTimes().get(i).getTime().toString()%>.</h1>
    </li>
    <%}%>
</ul>
<form action="/consschecha" method="get">
    <input type="submit" value="Заменить расписание">
</form>
<form action="/conprofstart" method="post">
    <input type="submit" value="Вернуться">
</form>
</body>
</html>
