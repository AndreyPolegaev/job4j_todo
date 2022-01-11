<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="ru">

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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>TodoList</title>
</head>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="js/main.js"></script>

<body>

<div class="container1">
    <h1>TodoList - список дел</h1>
    <a href="<%=request.getContextPath()%>/out" id="nameForm"> <c:out value="${user.name}"/> | Выйти</a>
    <br>
    <br>
</div>

<div class="container pt-2">
    <div class="row">
        <div class="card" id="mainForm" style="width: 95%">
            <div class="card-header" id="label1" style="font-weight: bold; font-size: larger">
                Добавьте задачу и выберите категорию задачи
            </div>
            <div class="card-body">
                <form action="<c:url value='/category'/>" method='POST' accept-charset="UTF-8">
                    <div class="input-group1">
                        <button type="submit" id="bt1">Добавить задачу</button>
                        <input type="text" id="description" name="desc" class="form-desc"
                               placeholder="Описание задачи?">
                        <button type="button" id="bt2" onclick="clear1()">Очистить список задач</button>
                        <br>
                        <br>
                        <div class="col-sm-5">
                            <select class="form-control" name="cIds" id="cIds" multiple>
                                <option></option>
                                <c:forEach items="${categories}" var="category">
                                    <option value='<c:out value="${category.id}"/>'>${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" id="selected" onchange="func1()">
                        <label class="form-check-label" id="label" for="selected">
                            Показать все задачи
                        </label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<br>
<br>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <table class="table" id="table">
                <thead class="thead-white">
                <tr>
                    <th scope="col">Описание</th>
                    <th scope="col">Дата создания</th>
                    <th scope="col">Статус</th>
                    <th scope="col">Выполнено</th>
                    <th scope="col">Удалить задачу</th>
                    <th scope="col">Автор</th>
                    <th scope="col">Категория</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <h4>Разработал - Полегаев А.В.</h4>
</div>
</body>
</html>