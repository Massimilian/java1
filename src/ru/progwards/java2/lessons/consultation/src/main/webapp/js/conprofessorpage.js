$(function () {
    $(this).keypress(function(e) {
        if(e.keyCode == 13 && e.key == 'Enter') {
            $('.forward').submit();
        }
        if (e.keyCode == 32) {
            $('.back').submit();
        }
    });
});

