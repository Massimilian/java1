$(function () {
    $(this).keypress(function (e) {
        if (e.keyCode == 13 && e.key == 'Enter') {
            if ($('input[name=date]').val() == '') {
                errWork($('input[name=date]'), e);
            } else {
                $('.forward').submit();
            }
        }
        if (e.keyCode == 32) {
            $('.back').submit();
        }
    });

    $(':submit').click(function (e) {
        if ($(this).attr('value') == "Выбрать дату (ENTER)") {
            $('input').each(function () {
                if ($(this).attr('name') == 'date' && $(this).val() == '') {
                    errWork($(this), e);
                }
            });
        }
    });

    function errWork(tag, e) {
        alert('Выберите дату.');
        tag.attr({
            'style': 'border:2px solid red'
        });
        e.preventDefault();
    }
});