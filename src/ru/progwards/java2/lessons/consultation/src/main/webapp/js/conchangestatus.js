$(function () {
    $('body .hidden').hide();
    $('body .open').click(function () {
        $('body .open').hide();
        $('body .hidden').show(1000);
    });
    $(':submit').click(function (e) {
        if ($(this).attr('value') == 'Войти') {
            if ($(':text').val() == "") {
                alert('Имя не может быть пустым');
                $(':text').attr({
                    'style': 'border:2px solid red'
                })
                e.preventDefault();
            }
            if ($(':password').val() == "") {
                alert('Пароль не может быть пустым');
                $(':password').attr({
                    'style': 'border:2px solid red'
                })
                e.preventDefault();
            }
        }
    });
});