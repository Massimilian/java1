<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 28.06.2022
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Full Info deleter.</title>
    <style>
        <%if (session.getAttribute("final").equals("Уничтожить школу - нажмите ещё раз.")) {%>
        body {
            background-color: #FF0000;
        }
        <%}%>
    </style>
</head>
<body>
<h2><span style="color: #BDE72E"><%=session.getAttribute("AllProfessors")%></span></h2>
<h2><span style="color: #BDE72E"><%=session.getAttribute("AllStudents")%></span></h2>
<form action="/consultallschool" method="post">
    <input type="submit" value="<%=session.getAttribute("final")%>">
</form>
<form action="/consultadmin">
    <input type="submit" value="Назад">
</form>
</body>
</html>
