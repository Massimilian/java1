$(function () {
    $(this).keypress(function (e) {
        if (e.keyCode == 13 && e.key == 'Enter') {
            if ($('input[id=date]').val() == '') {
                errWork($('input[id=date]'), e);
            } else {
                $('#forward').submit();
            }
        }
        if (e.keyCode == 32) {
            $('#back').submit();
        }
    });

    $(':submit').click(function (e) {
        if ($(this).attr('id') == "enter") {
            $('input').each(function () {
                if ($(this).attr('id') == 'date' && $(this).val() == '') {
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