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
    <title>Демо Bootstrap</title>
    <!-- CSS-настройки.-->
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <style>
        [class*="col"] {
            padding: 1px;
            background-color: #9ACD32;
            border: 2px solid #FF99FF;
            color: #0c4128;
        }
    </style>
</head>
<body>
<div class="container my-5" style="border: 2px solid #0d0d0d">
    <div class="row" style="height: 100px">
        <div class="col-lg">Текст 1</div>
        <div class="col-lg">Текст 2</div>
    </div>
</div>


<!-- Подключаем ajax - библиотеку (будем использовать в дальнейшем) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- JS настройки -->
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>