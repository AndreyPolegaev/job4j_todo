/** показать все задачи (выполненные и не выполненные,  data: принимает true/false из checkbox)
 *  перед выводом - очистка таблицы
 *  let cat = data[i].categories - получаем коллекцию категорий у Item
 *  let nameOfCategory = "" - конкатенация по категориям
 */
function func1() {
    var myTable = document.getElementById("table");
    var rowCount = myTable.rows.length;
    for (var x = rowCount - 1; x > 0; x--) {
        myTable.deleteRow(x);
    }
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/mainServlet',
        dataType: 'json',
        data: 'index=' + document.getElementById('selected').checked,
    }).done(function (data) {
        for (let i = 0; i < data.length; i++) {
            let nameOfCategory = "";
            let linkDelete = "http://localhost:8080/todo/deleteDB?id=" + data[i]['id'];
            let updateStatus = "http://localhost:8080/todo/UpdateStatus?id=" + data[i]['id'];
            let status;
            let currentStatus = data[i].done;
            if (currentStatus === false) {
                status = "не выполнено"
            } else {
                status = "выполнено";
            }
            let cat = data[i].categories;
            for (let j = 0; j < cat.length; j++) {
                nameOfCategory += cat[j].name + '<br>';
            }
            $('.table tr:last')
                .after(
                    '<tr>' +
                    '<td>' + data[i].description + '</td>' +
                    '<td>' + data[i].created + '</td>' +
                    '<td>' + status + '</td>' +
                    '<td>' + '<a href=' + updateStatus + '><img src="images/done.png" width="20" \n' +
                    '   height="20" alt="Пример"></a>' + '</td>' +
                    '<td>' + '<a href=' + linkDelete + '><img src="images/clear.png" width="20" \n' +
                    '   height="20" alt="Пример"></a>' + '</td>' +
                    '<td>' + data[i].user.name + '</td>' +
                    '<td>' + nameOfCategory + '</td>' +
                    '</tr>'
                );
        }
    }).fail(function (err) {
        console.log(err);
    });
}

/** Когда страница загружен выгружаем все НЕВЫПОЛНЕННЫЕ задачи из БД
 *  Из сервлета приходит List<Item> в виде JSON
 *  let cat = data[i].categories - получаем коллекцию категорий у Item
 *  let nameOfCategory = "" - конкатенация по категориям
 */
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/mainServlet',
        dataType: 'json',
        data: 'index=' + false
    }).done(function (data) {
        for (let i = 0; i < data.length; i++) {
            let nameOfCategory = "";
            let linkDelete = "http://localhost:8080/todo/deleteDB?id=" + data[i]['id'];
            let updateStatus = "http://localhost:8080/todo/UpdateStatus?id=" + data[i]['id'];
            let status;
            let currentStatus = data[i].done;
            if (currentStatus === false) {
                status = "не выполнено"
            } else {
                status = "выполнено";
            }
            let cat = data[i].categories;
            for (let j = 0; j < cat.length; j++) {
                nameOfCategory += cat[j].name + '<br>';
            }
            $('.table tr:last')
                .after(
                    '<tr>' +
                    '<td>' + data[i].description + '</td>' +
                    '<td>' + data[i].created + '</td>' +
                    '<td>' + status + '</td>' +
                    '<td>' + '<a href=' + updateStatus + '><img src="images/done.png" width="20" \n' +
                    '   height="20" alt="Пример"></a>' + '</td>' +
                    '<td>' + '<a href=' + linkDelete + '><img src="images/clear.png" width="20" \n' +
                    '   height="20" alt="Пример"></a>' + '</td>' +
                    '<td>' + data[i].user.name + '</td>' +
                    '<td>' + nameOfCategory + '</td>' +
                    '</tr>'
                );
        }
        category();
    }).fail(function (err) {
        console.log(err);
    });
});

/**
 * Вызывается из функции ready при загрузке страницы
 */
function category() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/category',
        dataType: 'json'
    }).done(function (data) {
        let caregories = "";
        for (let i = 0; i < data.length; i++) {
            caregories += "<option value=" + data[i].id + ">" + data[i].name + "</option>";
        }
        $('.form-control').html(caregories);

    }).fail(function (err) {
        alert(err);
    });
}

/** Очистка БД  */
function clear1() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/deleteAll',
        dataType: 'text'
    }).done(function (data) {
        document.location.href = 'http://localhost:8080/todo/index.jsp';
    }).fail(function (err) {
        console.log(err)
    });
}

/** Очистка БД  */
function clear1() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/deleteAll',
        dataType: 'text'
    }).done(function (data) {
        document.location.href = 'http://localhost:8080/todo/index.jsp';
    }).fail(function (err) {
        console.log(err)
    });
}


/**
 * Регистрация нового пользовател. Если пользователь с таким email был в БД - вывести сообщение
 * done(function (data) - срабазывает только когда есть входящие данные ответа из сервлета
 * валидация формы
 */
function reg() {
    let name = $('#name');
    let email = $('#email');
    let password = $('#password');
    let array = [name, email, password];
    for (let i = 0; i < array.length; i++) {
        if (array[i].val() === '') {
            alert("заполните все поля")
            return false;
        }
    }
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/reg',
        data: ({name: name.val(), email: email.val(), password: password.val()}),
        dataType: 'text'
    }).done(function (data) {
        if ($.trim(data)) {
            alert(data)
        } else {
            document.location.href = 'http://localhost:8080/todo/index.jsp';
        }
    }).fail(function (err) {
        console.log(err)
    });
}

/**
 * Авторизация: если email есть в базе и пароль совпадает то перенаправить на index.jsp
 * инчаче сообщение - пользователь не найден или пароль не совпадает (передается в done
 * из сервлета auth)
 * if ($.trim(data)) - если входящее сообщение не пустое, то выводим его.
 */
function auth() {
    let email = $('#email1');
    let password = $('#password1');
    let array = [email, password];
    for (let i = 0; i < array.length; i++) {
        if (array[i].val() === '') {
            alert("заполните все поля")
            return false;
        }
    }
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/auth',
        data: ({email: email.val(), password: password.val()}),
        dataType: 'text'
    }).done(function (data) {
        if ($.trim(data)) {
            alert(data)
        } else {
            document.location.href = 'http://localhost:8080/todo/index.jsp';
        }
    }).fail(function (err) {
        console.log(err)
    });
}

