$(function () {
    $('input').fadeTo(1, 0.7);

    $(this).keypress(function(e) {
        if (e.keyCode == 32) {
            $('#back').submit();
        }
    });

    $('input').mouseover(function () {
        $(this).fadeTo(500, 1);
        $(this).css({
            'border' : '1px solid green'
        });
    });

    $('input').mouseout(function () {
        $(this).fadeTo(1500, 0.5);
        $(this).css({
            'border' : 'none'
        });
    });
});
