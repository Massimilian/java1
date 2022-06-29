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
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h2><span style="color: #FF0000"><%=request.getSession().getAttribute("false")%></span></h2>
<h1>Добро пожаловать на страницу консультаций, <%=session.getAttribute("name")%>!</h1>
<h2>Выберите преподавателя, у которого Вы хотите консультироваться</h2>
<% ArrayList<Professor> professors = ((School) session.getAttribute("school")).getProfessors();
    for (int i = 0; i < professors.size(); i++) {%>
<ul>
    <form action = "/conswelcome" method="post">
    <input type="submit" value="<%= professors.get(i).getName()%>" name="<%= professors.get(i).getName()%>">
    <%=professors.get(i).getSchedule()%>
    </form>
</ul>
<% } %>
<form action = "/condelete" method="get">
    <input type="submit" value="Удалить консультацию">
</form>
<form action = "index.jsp">
    <input type="submit" value="Выйти">
</form>
</body>
</html>