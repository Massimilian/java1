<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 10.07.2022
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login by session</title>
    <link rel="stylesheet" type="text/css" href="css/session_login.css">
</head>
<body>
<section class="h-100 gradient-form" style="background-color: orchid">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-xl-10">
                <div class="card rounded-3 text-black">
                    <div class="row g-0">
                        <div class="col-lg-6">
                            <div class="card-body p-md-5 mx-md-4">
                                <div class="text-center">
                                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/lotus.webp"
                                         style="width: 185px;" alt="logo">
                                    <h4 class="mt-1 mb-5 pb-1">Дизайн формы "The Lotus Team"</h4>
                                </div>
                                <form action="/check" method="post">
                                    <p>Введите логин и пароль</p>
                                    <div class="form-outline mb-4">
                                        <input type="text" name="login" class="form-control"/>
                                        <label class="form-label">Логин</label>
                                    </div><br>
                                    <div class="form-outline mb-4">
                                        <input type="password" name="password" class="form-control" />
                                        <label class="form-label">Пароль</label>
                                    </div>

                                    <div class="text-center pt-1 mb-5 pb-1">
                                        <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" type="submit">Log in</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
