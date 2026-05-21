<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>用户注册页</title>
</head>
<body>
<div>
    <div>
        <label for="">Username：</label>
        <input type="text" class="UserName" id="UserName" onblur="checkUserName()">
        <span id="ver_UserNameMsg"></span>
    </div>
    <div>
        <label for="">Password：</label>
        <input type="password" class="Password" id="Password" onblur="checkPassword()">
        <span id="ver_PasswordMsg"></span>
    </div>
    <div>
        <label for="">Repeat Password：</label>
        <input type="password" class="RepeatPassword" id="RepeatPassword" onblur="checkRepeatPassword()">
        <span id="ver_RepeatPasswordMsg"></span>
    </div>

    <div>
        <button type="button" class="submit">submit</button>
    </div>
</div>
</body>
</html>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script>
    var flagUser = false;
    var flagPwd = false;
    var flagRpwd = false;

    function checkUserName() {
        var userName = $("#UserName").val();
        var regex = /^[a-zA-Z_][a-zA-Z0-9_]*$/;
        if (regex.test(userName) == false) {
            $("#ver_UserNameMsg").html("<font color='red'>只能以英文字母或下划线开头,只能包含英文字母，下划线或数字</font>");
            return false;
        } else {
            $("#ver_UserNameMsg").html("<font color='green'>√</font>");
            flagUser = true
        }
    }

    function checkPassword() {
        var password = $("#Password").val();
        var regex = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$).{6,}$/;

        if (regex.test(password) == false) {
            $("#ver_PasswordMsg").html("<font color='red'>长度在 6 位以上，不能含有 3 位以上的连续数字，必须有大写字母，小写字母或数字中的两项 </font>");
        } else {
            $("#ver_PasswordMsg").html("<font color='green'>√</font>");
            flagPwd = true
        }
    }

    function checkRepeatPassword() {
        var password = $("#Password").val();
        var RepeatPassword = $("#RepeatPassword").val();

        if (password == RepeatPassword && RepeatPassword != "" && RepeatPassword != null && RepeatPassword != undefined) {
            $("#ver_RepeatPasswordMsg").html("<font color='green'>√</font>");
            flagRpwd = true
        } else {
            $("#ver_RepeatPasswordMsg").html("<font color='red'>两次密码不一致或密码为空</font>");
        }
    }

    $(".submit").on('click', function (e) {
        if (flagUser && flagPwd && flagRpwd) {
            var userName = $("#UserName").val();
            var password = $("#Password").val();
            var RepeatPassword = $("#RepeatPassword").val();
            $.post('/index/register',
                {
                    userName: userName,
                    password: password,
                    repeatPassword: RepeatPassword
                },
                function (res) {
                    res = JSON.parse(res)
                    console.log(res)
                    if (res.code == 200) {
                        alert(res.msg);
                    }
                }
            )
        } else {
            checkUserName()
            checkPassword()
            checkRepeatPassword()
        }
    })
</script>
