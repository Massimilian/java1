<%@ page import="org.example.servlets.consult2.classes.School" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 21.06.2022
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Deleted!</title>
</head>
<body>
<h4>Консультация успешно удалена!</h4>
<%session.setAttribute("school", School.getSchool());%>
<form action="/conswelcome" method="get">
    <input type="submit" value="Вернуться">
</form>
</body>
</html>
