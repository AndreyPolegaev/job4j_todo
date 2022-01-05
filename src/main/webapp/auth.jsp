<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" type="text/css" href="css/styleReg.css">

    <title>Авторизация</title>
</head>


<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="js/main.js"></script>

<body>
<div class="container pt-6">
    <div class="row justify-content-center">
        <div class="card" style="width: 400px">
            <div class="form-container">
                <h3 class="title">Авторизация</h3>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label>Email</label>
                        <input class="form-control" type="email" id="email1" placeholder="Ваш email?">
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input class="form-control" type="password" id="password1" placeholder="Пароль?">
                    </div>
                    <button type="button" class="btn btn-default" onclick="auth()">Войти</button>
                    <br>
                    <br>
                    <button type="button" class="btn btn-default" onclick="document.location='reg.jsp'">Зарегистрироваться</button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
