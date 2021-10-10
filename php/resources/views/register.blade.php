<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>表单样式</title>
    <style type="text/css">
        * {
    font-size: 12px;
            margin: 0;
            padding: 0;
        }


        input {
    width: 320px;
            height: 24px;
            border: 1px solid #999;
            border-radius: 4px;
        }


        .formErr {
    border: 1px solid red;
        }


        .form .label {
    display: block;
    float: left;
    width: 128px;
            height: 40px;
            line-height: 40px;
            text-align: end;
        }


        .form .txt {
    display: block;
    float: left;
    width: 340px;
            height: 40px;
            line-height: 40px;
            padding-left: 16px;
        }

        .form .notice_span {
    display: block;
    float: left;
    width: 640px;
            height: 40px;
            line-height: 40px;
            padding-left: 16px;
        }


        .form button {
    width: 56px;
            height: 24px;
            background-color: green;
            border: 0;
            border-radius: 4px;
            color: white;
        }


        .form .errTips {
    width: 226px;
            background-color: lightpink;
            color: darkred;
            border-radius: 4px;
            margin-left: 144px;
            margin-top: 6px;
            margin-bottom: 4px;
            padding: 16px 48px;
        }
    </style>
</head>
<body>
<div class="form">
    <form action="http://localhost:3000/api/register" method="post">
    <div>
        <span class="label">Username:</span>
        <span class="txt"><input type="text" name="username" placeholder="Username" id="username_id"/></span>
        <span class="notice_span" id="notice_username">只能以英文字母或下划线开头且只能包含英文字母,下划线或数字</span>
    </div>
    <div style="clear: both"></div>
    <div>
        <span class="label">Password:</span>
        <span class="txt"><input type="password" name="password" placeholder="Password" class="formErr"/></span>
        <span class="notice_span" id="notice_password">长度在6位以上,不能含有3位或以上的连续数字，如 123、234 等,必须有大写字母，小写字母或数字中的两项</span>
    </div>
    <div style="clear: both"></div>
    <div>
        <span class="label">Repeat Password:</span>
        <span class="txt"><input type="password" name="repeat_password" placeholder="Repeat Password"/></span>
        <span class="notice_span" id="notice_repeat_password"></span>
    </div>
    <div style="clear: both"></div>
    <!--<div class="errTips">-->
        <!--<ul>-->
            <!--<li>密码长度不能小于6个字符！</li>-->
            <!--<li>密码不能为空！</li>-->
            <!--<li>两次密码输入不一致！</li>-->
        <!--</ul>-->
    <!--</div>-->
    <div style="clear: both"></div>
    <div>
        <span class="label"></span>
        <span class="txt"><input type="submit" value="Submit"/> </span>
    </div>
    </form>
</div>
</body>
</html>

