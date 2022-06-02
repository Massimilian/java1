<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 02.05.2022
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success!</title>
</head>
<body>
<h1>Вход <%=request.getParameter("username")%> выполнен успешно</h1>
<form action="index.jsp">
  <input type="submit" value="Выход">
</form>

</body>
</html>
