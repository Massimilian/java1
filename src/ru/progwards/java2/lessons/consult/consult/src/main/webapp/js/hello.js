$(function () {
    $('p').each(function () {
        if ($(this).attr('src') == '22test.jsp') { // проверка на наличие атрибута src, который равен '22test.jsp'
            alert($(this).text()); // если условие выполнено - выполняем действие
        }
    })
});
