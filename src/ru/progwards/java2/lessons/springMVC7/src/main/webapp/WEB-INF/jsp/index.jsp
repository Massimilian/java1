<%@ page import="app.services.Task" %>
<%@ page import="java.util.List" %>
<%-- В следующей строке мы подключаем библиотеку тегов jstl
и помечаем, что обращаться к ней будем через тег <c> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 08.05.2023
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%-- Внимание! Если убрать следующую строчку, то будут проблемы с кодировкой!--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- В следующей строчке мы подключаем библиотеку bootstrap --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <title>Index</title>
</head>
<body>
<div class="container">
    <div class="row">
        <%--Растягиваем следующий элемент на всю страницу--%>
        <div class="col-12">
            <%--Отображение делаем в виде кнопки (mb-3 - это отступ)--%>
            <a class="btn btn-primary mb-3" href="/task/add">Создать</a>
        </div>
    </div>
    <%--Создаём таблицу--%>
    <table class="table table-hover">
        <%--Заголовок таблицы - помечаем голубым цветом--%>
        <thead bgcolor="AQUA">
        <%--Напоминание - tr = строка, td = колонка --%>
            <tr>
                <td>ID</td>
                <td>NAME</td>
                <td>CREATED</td>
                <td>PRIORITY</td>
                <td>TYPE</td>
            </tr>
        </thead>
        <%--"тело" таблицы--%>
        <tbody>
        <%--Используем метод библиотеки jstl для перечисления--%>
        <c:forEach items="${list}" var="item">
            <tr>
                <td><c:out value="${item.id}"/></td>
                <td><c:out value="${item.name}"/></td>
                <td><c:out value="${item.created}"/></td>
                <td><c:out value="${item.priority}"/></td>
                <td><c:out value="${item.type}"/></td>
                <%--Создаём ссылку для перехода на другой адрес для редактирования--%>
                <td><a href="/task/edit/${task.id}">Редактировать</a></td>
                <%--Создаём ссылку для перехода на другой адрес для удаления--%>
                <td><a href="/task/delete?id=${task.id}">Удалить</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
