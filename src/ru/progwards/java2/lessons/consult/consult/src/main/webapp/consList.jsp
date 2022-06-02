<%@ page import="org.example.Consult" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 11.04.2022
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of consultations</title>
    <link href="css/consList.css" rel="stylesheet" id="bootstrap-css">
</head>
<body>
<h1>Список доступных консультаций</h1>
<h6><%= Consult.show()%></h6>
<form action="/consult">
    <button type="submit"> Вернуться в главное меню</button>
</form>

</body>
</html>
