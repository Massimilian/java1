<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 20.08.2022
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajax test</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
<button type="button" id="get">Получить текст с сервера</button>
<div id="result"></div>
</body>

<script>
    function getData() {
        // URL на который будем отправлять GET запрос
        const requestURL = 'receiver.php';
        const xhr = new XMLHttpRequest();
        xhr.open('GET', requestURL);
        xhr.onload = () => {
            if (xhr.status !== 200) {
                return;
            }
            document.querySelector('#result').innerHTML = xhr.response;
        }
        xhr.send();
    }
    // при нажатию на кнопку
    document.querySelector('#get').addEventListener('click', () => {
        getData();
    });
</script>
</html>
