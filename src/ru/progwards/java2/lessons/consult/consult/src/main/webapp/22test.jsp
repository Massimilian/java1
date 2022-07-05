<%@ page import="java.util.ArrayList" %><%--
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
    <title>Демо Bootstrap</title>
    <!-- Ниже подключаются CSS-настройки.-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css">
</head>
<body>
<!-- Контейнер, который будет в себя что-то вмещать-->
<div class="container">
    <!--Выстраивает последующие div-ы в строку-->
    <div class="row">
        <!-- Создаём колонки -->
    <div class="col-sm"><h1>Привет мир!</h1></div>
    <div class="col-sm"><h1>Привет мир!</h1></div>
    <div class="col-sm"><h1>Привет мир!</h1></div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
<!-- Подключаются настройки Popper и JS -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.min.js"></script>
</body>
</html>