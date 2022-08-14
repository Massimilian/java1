$(function () {
    var nameParams = ['name', 'nameDel', 'nameProf', 'nameProfDel'];
    var passwordParams = ['password', 'passwordProf']
    var ns = true;
    var ds = true;
    var np = true;
    var dp = true;
    $('span').attr({
        'style': 'color: #9ACD32',
        'font-size': '20px'
    });

    $('div').attr({
        'style': 'color: orchid'
    }).hide();

    $('h2').click(function () {
        $('div').hide(500);
        if ($(this).html() == 'Ввести нового студента:') {
            if (ns) {
                $('.newStudent').show(1000).ns = false;
            } else {
                $('.newStudent').hide(1000);
                ns = true;
            }
        }
        if ($(this).html() == 'Удалить студента:') {
            if (ds) {
                $('.delStudent').show(500);
                ds = false;
            } else {
                $('.delStudent').hide(500);
                ds = true;
            }
        }
        if ($(this).html() == 'Ввести нового преподавателя:') {
            if (np) {
                $('.newProfessor').show(1000);
                np = false;
            } else {
                $('.newProfessor').hide(1000);
                np = true;
            }
        }
        if ($(this).html() == 'Удалить преподавателя:') {
            if (dp) {
                $('.delProfessor').show(500);
                dp = false;
            } else {
                $('.delProfessor').hide(500);
                dp = true;
            }
        }
    });

    $(':submit').click(function (e) {
        if ($(this).attr('name') == 'add') {
            searchAttr($('.newStudent :text'), e);
        }
        if ($(this).attr('name') == 'delete') {
            searchAttr($('.delStudent :text'), e);
        }
        if ($(this).attr('name') == 'addProf') {
            searchAttr($('.newProfessor :text'), e);
        }
        if ($(this).attr('name') == 'deleteProf') {
            searchAttr($('.delProfessor :text'), e);
        }
    });

    function searchAttr(attr, e) {
        attr.each(function () {
            checkParam($(this), nameParams, e, 'Не заполнено имя.');
            checkParam($(this), passwordParams, e, 'Не заполнен пароль.');
        });
    }

    function checkParam(object, params, e, text) {
        for (let i = 0; i < params.length; i++) {
            if (object.attr('name') == params[i] && object.val() == '') {
                alert(text);
                e.preventDefault();
                break;
            }
        }
    }
});
