$(function () {
    $(this).keypress(function (e) {
        if (e.keyCode == 32) {
            $('form').submit();
        }
    });
});