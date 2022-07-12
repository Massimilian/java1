<%--
  Created by IntelliJ IDEA.
  User: vasal
  Date: 10.07.2022
  Time: 7:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cookie entry</title>
    <link rel="stylesheet" type="text/css" href="css/cookieset.css">
</head>
<body>
<jsp:include page="cookie_shower.jsp"></jsp:include>
<section class="vh-100 gradient-custom">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card bg-dark text-white" style="border-radius: 1rem;">
                    <div class="card-body p-5 text-center">
                        <div class="mb-md-5 mt-md-4 pb-5">
                            <h1 class="fw-bold mb-2 text-uppercase">Введите текст</h1>
                            <form action="/cookie1" method="post">
                                <div class="form-outline form-white mb-4">
                                    <input type="text" name="word" class="form-control form-control-lg"/>
                                    <label class="form-label">Текст</label>
                                </div>
                                <br>
                                <button class="btn btn-outline-light btn-lg px-5" type="submit">Войти</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
