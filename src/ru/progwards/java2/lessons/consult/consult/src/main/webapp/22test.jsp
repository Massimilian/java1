<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 29.06.2022
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="js/jquery-3.6.0.min.js"></script>
    <script src="js/work.js"></script>
    <title>Демо jQuery</title>
</head>
<body>
1. ${param.one} <br>
2. ${param.two} <br>
3. ${param.three} <br>
4. ${param.four} <br>
5. ${param.five} <br>
6. ${param.six} <br>
7. ${param.seven} <br>
8. ${param.eight} <br>
9. ${param.nine} <br>
<form action="22test.jsp">
    <input type="checkbox" name="one" value="one"> one
    <input type="checkbox" name="two" value="two"> two
    <input type="checkbox" name="three" value="three"> three
    <br>
    <input type="radio" name="four"> four
    <input type="radio" name="five"> five
    <br>
    <input type="file" name="six"> six
    <br>
    <input type="button" name="seven" value="Кнопка" onclick="alert('Нажата кнопка seven')">
    <br>
    <input type="text" name="eight"> eight
    <br>
    <input type="password" name="nine"> nine
    <br>
    <input type="reset">
    <br>
    <input type="submit" value="отправить">
</form>
</body>
</html>