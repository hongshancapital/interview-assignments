$(function () {
    $('input[type="submit"]').click(function () {
        var username = $.trim($('#username').val()),
            password = $.trim($('#password').val()),
            password1 = $.trim($('#password1').val());

        var reg = /^[a-zA-Z_]([-_a-zA-Z0-9]+)$/;
        if(!reg.test(username)){
            alert('username 只能以英文字母或下划线开头，且只能包含英文字母，下划线或数字');
            return;
        }

        reg = /([a-zA-Z0-9]{6,})$/;
        if(!reg.test(password)){
            alert('password 必须有大写字母，小写字母或数字中的两项，长度在 6 位以上');
            return;
        }

        reg = /\d{4}/g;
        if(reg.test(password)){
            alert('password 必须不能含有 3 位以上的连续数字');
            return;
        }

        if (password != password1) {
            alert('两次输入密码不一致');
            return;
        }

        $.post('/api/register', {username: username, password: password}, function (data) {
            if (data.errcode) {
                alert(data.msg);
                return;
            }
            $('body').append(data.msg);
        });
    });
});