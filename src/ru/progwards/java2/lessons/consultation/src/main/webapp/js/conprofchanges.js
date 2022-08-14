$(function () {
    $(this).keypress(function(e) {
        if(e.keyCode == 13 && e.key == 'Enter') {
            if (!radios()) {
                alert("Выберите поле для изменений.");
            } else {
                $('#forward').submit();
            }
        }
        if (e.keyCode == 32) {
            $('#back').submit();
        }
    });

    $(':submit').click(function (e) {
       if ($(this).attr("id") == "enter" && !radios()) {
           alert("Выберите поле для изменений.");
           e.preventDefault();
       }
    });

    function radios() {
        var radiosChecked = false;
        $(':radio:checked').each(function(){
            radiosChecked = true;
        });
        return radiosChecked;
    }
});