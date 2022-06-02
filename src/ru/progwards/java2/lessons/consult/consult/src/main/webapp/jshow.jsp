<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 07.05.2022
  Time: 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show place</title>
</head>
<body>
<h1><%= request.getAttribute("head")%></h1><br>
<img src="<%= request.getAttribute("location")%>" height="350" width="350">
<h2><%= request.getAttribute("body")%></h2>
<h4>Общая оценка = <%= request.getAttribute("likes")%> </h4>
<h5>Дата публикации - <%=request.getAttribute("date")%>. Количество просмотров - <%= request.getAttribute("looks")%>.</h5>
<h3>Автор - <%= request.getAttribute("author")%></h3>
<img src="<%= request.getAttribute("picture")%>" height="50" width="50">
<form action="/jvote" method="get">
    <input type="radio" name="good"><i>понравилось</i>
    <input type="radio" name="bad"><i>не понравилось</i>
    <input type="submit" value="проголосовать">
</form>
<form action="/jsecondpage" method="get">
    <input type="submit" value="Вернуться назад.">
</form>
</body>
</html>
