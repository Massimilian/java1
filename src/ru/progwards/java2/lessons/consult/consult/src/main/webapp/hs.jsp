<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 03.05.2022
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HelloServlet</title>
</head>
<body>
<h4>Page for Filter using</h4>
<form action="index.jsp">
    <%@ page import="java.util.Date"%>
    <%= new Date()%>

    <input type="submit" value="Back to the main page">
</form>
</body>
</html>
