$(document).ready(function () {
    $(':text').attr('disabled', 'disabled'); // делаем поле text неизменяемым
    $(':text').removeAttr('disabled'); // снимаем флажок несменяемости
});
