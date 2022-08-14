$(function () {
    $(this).keypress(function (e) {
        if (e.keyCode == 13 && e.key == 'Enter') {
            var tag = $('input[id=time]');
            if (tag.val() == '') {
                errWork($(tag), e);
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
                if ($(this).attr('id') == 'time' && $(this).val() == '') {
                    errWork($(this), e);
                }
            });
        }
    });

    function errWork(tag, e) {
        alert('Выберите время.');
        tag.attr({
            'style': 'border:2px solid red'
        });
        e.preventDefault();
    }

});