<%@ page import="org.example.servlets.consult2.classes.Professor" %>
<%@ page import="org.example.servlets.consult2.classes.Consultation" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 22.06.2022
  Time: 7:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Professor's page</title>
</head>
<body>
<h1>Добро пожаловать, <%=session.getAttribute("name")%>!</h1>
<h1>Предстоящие лекции:</h1>
<ul>
    <%
        ArrayList<Consultation> consultations = ((Professor)session.getAttribute("professor")).getConsults();
        for (int i = 0; i < consultations.size(); i++) {
            %>
    <li><h1><%=consultations.get(i).getStart().toLocalDate()%>, в <%=consultations.get(i).getStart().toLocalTime()%>;
        тема консультации - "<%=consultations.get(i).getThema()%>", студент - <%=consultations.get(i).getStudent()%>.</h1></li>
    <%
        }
    %>
</ul>
<form action="/professorchanges" method="get">
    <input type="submit" value="Изменения в учётную запись.">
</form>
<form action="index.jsp">
    <input type="submit" value="Выход">
</form>
</body>
</html>
