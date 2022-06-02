<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 11.04.2022
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main Consult</title>
    <link href="css/consult.css" rel="stylesheet" id="bootstrap-css">
</head>
<body>
<h1>Это стартовая страница консультаций</h1>
<form action="/write">
    <button type="submit"> Записаться на консультацию</button>
</form>
<form action="/delete">
    <button type="submit"> Отменить консультацию</button>
</form>
<form action="/show">
    <button type="submit"> Получить список консультаций</button>
</form>
<form action="index.jsp">
    <button type="submit"> Вернуться в основное меню</button>
</form>
</body>
</html>
