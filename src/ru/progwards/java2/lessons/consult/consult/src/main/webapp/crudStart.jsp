<%@ page import="java.util.HashMap" %>
<%@ page import="org.example.servlets.crudExample.classes.Task" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 16.05.2022
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CRUD start page.</title>
</head>
<body>
<table border="1px solid black\">
    <tr bgcolor="aqua">
        <th>
            <h4>Редактировать</h4>
        </th>
        <th>
            <h4>Удаление</h4>
        </th>
        <th>
            <h4>Дата создания</h4>
        </th>
        <th>
            <h4>Содержание</h4>
        </th>
    </tr>
    <% HashMap<String, Task> tasks = (HashMap<String, Task>) request.getSession().getAttribute("tasks");
        for (Map.Entry<String, Task> entry : tasks.entrySet()) {%>
    <tr bgcolor="#7fffd4">
        <th>
            <form action="/crudChange" method="get">
                <input type="submit" value="<%=entry.getValue().getName()%>" name="<%=entry.getValue().getName()%>">
            </form>
        </th>
        <th>
            <form action="/crudDelete" method="post">
                <input type="submit" value="удалить" name="<%=entry.getValue().getName()%>">
            </form>
        </th>
        <th><h6><%out.print(entry.getValue().getDate());%></h6></th>
        <th><h6><%out.print(entry.getValue().getValue());%></h6></th>
    </tr>
    <%}%>
</table>
<form action="/crudNew" method="get">
    <input type="submit" value="Новая заметка">
</form>
<form action="index.jsp" method="post">
    <input type="submit" value="Возврат в главное меню.">
</form>
</body>
</html>
