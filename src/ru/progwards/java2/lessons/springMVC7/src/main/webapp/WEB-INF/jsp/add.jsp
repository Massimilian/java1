<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 24.05.2023
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%-- Подключаем Bootstrap --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <%-- Подключаем "датапикер" - специальный инструмент Bootstrap по выбору даты --%>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.min.css"
          rel="stylesheet"/>

    <title>Add new task</title>
</head>
<body>
<%-- Оформляем всё в отдельный класс-контейнер --%>
<div class="container">
    <%-- Страница будет посылать запрос на саму себя методом post--%>
    <form action="/task/add" method="post">
        <%-- подключаем несколько полей для заполнения--%>
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name">
        </div>
        <%-- это поле будет связано с датапикером по id (в скрипте ниже будет вызван соответствующий метод)--%>
        <div class="form-group">
            <label for="created">Created</label>
            <input type="text" class="form-control" id="created" name="created">
        </div>
        <%-- выбор приоритетности - делаем через Bootstrap форму выбора --%>
        <div class="form-group">
            <label for="priority">Priority</label>
            <select id="priority" name="priority" class="form-control">
                <option value="MAJOR">MAJOR</option>
                <option value="MINOR">MINOR</option>
                <option value="CRITICAL">CRITICAL</option>
                <option value="BLOCKER">BLOCKER</option>
            </select>
        </div>
        <%-- выбор типа - также делаем через Bootstrap форму выбора--%>
        <div class="form-group">
            <label for="type">Type</label>
            <select  id="type" name="type" class="form-control">
                <option value="TASK">TASK</option>
                <option value="BUG">BUG</option>
            </select>
        </div>
        <%-- Кнопка, нажав на которую мы отсылаем ("сабмиттим") информацию на сервер --%>
        <input type="submit" class="btn btn-primary" value="Сохранить">
    </form>
</div>
<%-- Необходимые для грамотной отработки скрипты --%>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"></script>
<%-- скрипт отработки датапикера --%>
<script type="text/javascript">
    $(function () {
        $('#created').datepicker();
    });
</script>
</body>
</html>
