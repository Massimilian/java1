$(function () {
    $(":submit").click(function (e) {
        var login = $('#login').val();
        var password = $('#password').val();
        var repeat = $('#repeat').val();
        if (login == '') {
            colorizing($('#login'), e, "Логин не заполнен.");
        } else if (password == '') {
            colorizing($('#password'), e, "Пароль не заполнен.");
        } else if (password.length < 8) {
            colorizing($('#password'), e, "Пароль должен быть не менее 8 символов.");
        } else if (password.search(/[A-Z]/) == -1) {
            colorizing($('#password'), e, "В пароле должен быть хотя бы один заглавный символ.");
        } else if (password.search(/[a-z]/) == -1) {
            colorizing($('#password'), e, "В пароле должен быть хотя бы один прописной символ.");
        } else if (password.search(/\d/) == -1) {
            colorizing($('#password'), e, "В пароле должна быть хотя бы одна цифра.");
        } else if (password.search(/[^A-Za-z0-9]/) == -1) {
            colorizing($('#password'), e, "В пароле должен быть хотя бы один спецсимвол.");
        } else if (password != repeat) {
            $('#repeat').attr('style', 'border:2px solid red');
            colorizing($('#password #repeat'), e, "Пароли должны совпадать.");
        }
    });

    function colorizing(tag, e, text) {
        tag.attr('style', 'border:2px solid red');
        alert(text);
        e.preventDefault();
    }
});