<%@ page import="org.example.servlets.consult2.classes.Professor" %><%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 23.06.2022
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Professor's data changer</title>
</head>
<body>
<span style="color: #FF0000"><h1><%=session.getAttribute("false")%></h1></span>
<form action="conschand" method="get">
    <h3>Введите Ваш новый ник:</h3>
    <input type="text" value="<%=((Professor)session.getAttribute("professor")).getName()%>" name="name">
    <h3>Введите Ваш новый пароль</h3>
    <input type="password" name="password">
    <h3>Повторите пароль:</h3>
    <input type="password" name="repeat">
    <input type="submit" value="ввести данные">
</form>
</body>
</html>
