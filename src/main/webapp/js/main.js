/** показать все задачи (выполненные и не выполненные,  data: принимает true/false из checkbox)
 *  перед выводом - очистка таблицы
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
            let linkDelete = "http://localhost:8080/todo/deleteDB?id=" + data[i]['id'];
            let updateStatus = "http://localhost:8080/todo/UpdateStatus?id=" + data[i]['id'];
            let status;
            let currentStatus = data[i].done;
            if (currentStatus === false) {
                status = "не выполнено"
            } else {
                status = "выполнено";
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
                    '</tr>'
                );
        }
    }).fail(function (err) {
        console.log(err);
    });
}

/** Когда страница загружен выгружаем все НЕВЫПОЛНЕННЫЕ задачи из БД  */
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/mainServlet',
        dataType: 'json',
        data: 'index=' + false
    }).done(function (data) {
        for (let i = 0; i < data.length; i++) {
            let linkDelete = "http://localhost:8080/todo/deleteDB?id=" + data[i]['id'];
            let updateStatus = "http://localhost:8080/todo/UpdateStatus?id=" + data[i]['id'];
            let status;
            let currentStatus = data[i].done;
            if (currentStatus === false) {
                status = "не выполнено"
            } else {
                status = "выполнено";
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
                    '</tr>'
                );
        }
    }).fail(function (err) {
        console.log(err);
    });
});

/**
 Добавление задачи в БД.Отправляем в сервлет значение из input id="description"
 Валидация формы на пустое поле
 */
function uploadInDB() {
    let description = $('#description');
    if (description.val() == '') {
        alert('Заполните поле')
        return false;
    }
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/mainServlet',
        data: 'desc=' + $('.form-desc').val(),
        dataType: 'json'
    }).done(function (data) {
        document.location.href = 'http://localhost:8080/todo/index.jsp';
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