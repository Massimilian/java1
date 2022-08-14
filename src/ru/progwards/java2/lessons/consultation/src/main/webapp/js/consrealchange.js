$(function () {
    var radioClicked = false;
    var timeStartChecked = false;
    var timeStopChecked = false;
    var timeOfFade = 1500;

    $('.startTime').fadeOut();
    $('.finishTime').fadeOut();
    $('.save').fadeOut();

    $('.dayOfWeek').click(function () {
        $('.startTime').fadeIn(timeOfFade);
    });

    $('.startTime').click(function () {
        $('.finishTime').fadeIn(timeOfFade);
    });

    $('.finishTime').click(function () {
        $('.save').fadeIn(timeOfFade);
    })

    $('form h5').hover(function (e) {
        $(this).css({
            'color': 'green',
            'background': '#FFFFE0'
        })
    }, function () {
        $(this).css({
            'color': 'black',
            'background': 'white'
        });
    });

    $(':submit').click(function (e) {
        if ($(this).attr('value') == "Сохранить") {
            $(':radio:checked').each(function () {
                radioClicked = true;
            });
            if ($('#start').val() != "") {
                timeStartChecked = true;
            }
            if ($('#start').val()) {
                timeStopChecked = true;
            }
            result(e);
        }
    });


    function result(e) {
        if (!radioClicked) {
            alert('Вы не выбрали рабочий день недели. Выберите и отправьте данные снова.');
            $('.dayOfWeek').css({
                'border': '1px solid red'
            });
        } else if (!timeStartChecked) {
            alert("Введите время начала консультаций");
            $('.startTime').css({
                'border': '1px solid red'
            });
        } else if (!timeStopChecked) {
            alert("Введите время конца консультаций");
            $('.finishTime').css({
                'border': '1px solid red'
            });
        }

        if (!radioClicked || !timeStartChecked || !timeStopChecked) {
            e.preventDefault();
        }
    }
});