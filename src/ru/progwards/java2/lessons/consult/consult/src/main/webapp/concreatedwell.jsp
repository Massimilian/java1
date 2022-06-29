<%@ page import="org.example.servlets.consult2.classes.School" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 19.06.2022
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Well done!</title>
</head>
<body>
<h2>Консультация добавлена. Не забудьте про Вашу консультацию, которая произойдёт <%=session.getAttribute("consultDate")%> в <%=session.getAttribute("consultTime")%>.</h2>
<h2>Ваш преподаватель - <%=session.getAttribute("professor")%>.</h2>
<%session.setAttribute("professor", null);
session.setAttribute("school", School.getSchool());
session.setAttribute("consultDate", null);
session.setAttribute("thema", null);
session.setAttribute("consultTime", null);%>
<form action="/conswelcome" method="get">
    <input type="submit" value="Вернуться">
</form>
</body>
</html>
