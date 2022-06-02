<%@ page import="org.example.servlets.testTask.classes.InfoKeeper" %>
<%@ page import="org.example.servlets.testTask.classes.Place" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>

<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 06.05.2022
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Second Page</title>
</head>
<body>
<h1>Путешествия:</h1><br>
<% Iterator<Place> iterator = ((ArrayList<Place>)request.getAttribute("places")).iterator(); %>
<%while (iterator.hasNext()) {
String head = iterator.next().getHeader();%>
<form action="/jsecondpage" method="post">
    <input type="submit" value="<%=head%>" name="<%=head%>">
</form>
</form>
<%}%>
<form action="/jnewplace" method="get">
    <input type="submit" value="Создать новое воспоминание.">
</form>
<form action="/jstart" method="get">
    <input type="submit" value="Вернуться в главное меню">
</form>
</body>
</html>
