<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 28.05.2022
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create date</title>
</head>
<body>
<h2><span style="color: #FF0000"><%=request.getSession().getAttribute("false")%></span></h2>
<form action="/consultation2create" method="post">
    <h3>Выберите дату начала занятий</h3><br>
    <input type="date" name="date"><br>
    <input type="submit" value="Выбрать дату">
</form>
<form action="index.jsp">
    <input type="submit" value="Выйти">
</form>

</body>
</html>
