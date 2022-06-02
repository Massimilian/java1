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
<h1>Добро пожаловать на страницу консультаций</h1>
<h2>Выберите преподавателя, у которого Вы хотите консультироваться</h2>
<% ArrayList<Professor> professors = ((School) session.getAttribute("school")).getProfessors();
    for (int i = 0; i < professors.size(); i++) {%>
<ul>
    <% out.print(professors.get(i).getName());%>
    <% out.print(professors.get(i).getSchedule());%>
</ul>
<% } %>
</body>
</html>