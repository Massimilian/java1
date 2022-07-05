<%@ page import="org.example.servlets.consult2.classes.Consultation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDateTime" %>
<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 21.06.2022
  Time: 7:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Delete consultation</title>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.consultations == null}">
        <h2>Консультации не найдены!</h2>
    </c:when>
    <c:when test="${sessionScope.consultations != null}">
        <% ArrayList<Consultation> consultations = (ArrayList<Consultation>) session.getAttribute("consultations");
            for (int i = 0; i < consultations.size(); i++) {
                LocalDateTime ldt = consultations.get(i).getStart();
                String temp = ldt.toLocalDate() + ", " + ldt.toLocalTime().getHour() + ":" +
                        (ldt.toLocalTime().getMinute() < 10 ? "0" + ldt.toLocalTime().getMinute() : ldt.toLocalTime().getMinute());
                String name = temp + consultations.get(i).getProfessor();%>
        <ul>
            <form action="/condelete" method="post">
                <li><input type="submit" value="<%=temp%>" name="<%=name%>"></li>
                <h5>"<%=consultations.get(i).getThema()%>", преподаватель - <%=consultations.get(i).getProfessor()%>.</h5><br>
            </form>
        </ul>
        <%}%>
    </c:when>
</c:choose>
<form action="/conswelcome" method="get">
    <input type="submit" value="Вернуться на предыдущую страницу">
</form>
</body>
</html>