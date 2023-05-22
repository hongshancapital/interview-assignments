<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Homework</title>
    <link rel="stylesheet" type="text/css" href="./css/layui.css">
    <script src="./js/layui.js"></script>
    <style>
        .content .layui-row {text-align: center;}
        .content .layui-elem-field legend {margin: 0; padding: 0;}
        .content .layui-form-item {display: inline-block; margin: 0; width: 50%;}
        .content .layui-form-label {width: 30%;}
        .content .layui-input-inline {width: 70%;margin: 0;}
    </style>
</head>
<body>
<div class="layui-container content">
    <div class="layui-row">
        <fieldset class="layui-elem-field layui-field-title">
            <legend>【账号注册】</legend>
        </fieldset>
        <form class="layui-form layui-form-pane" action="javascript:;">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                    <input type="text" name="username" lay-verify="required" placeholder="请输入用户名" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="space-div"></div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                    <input type="text" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="space-div"></div>
            <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-inline">
                    <input type="text" name="password_conf" lay-verify="required" placeholder="请再次输入密码"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="space-div"></div>
            <div class="layui-form-item">
                <button class="layui-btn" lay-filter="register" lay-submit="">注册</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    layui.use(['form', 'jquery'], function () {
        $ = layui.jquery;
        form = layui.form;
        send_flag = false;
        form.on('submit(register)', function (data) {
            if (send_flag) {
                return false;
            }
            send_flag = true;
            $.ajax({
                url: "/api/register.php",
                data: {
                    'username': $('.content input[name="username"]').val(),
                    'password': $('.content input[name="password"]').val(),
                    'password_conf': $('.content input[name="password_conf"]').val(),
                },
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.status == 'success') {
                        layer.msg('注册成功', {anim: 6});
                    } else {
                        layer.msg(data.message, {anim: 6});
                    }
                    send_flag = false;
                },
                error: function () {
                    layer.msg('接口异常，请稍后再试！', {anim: 6});
                    send_flag = false;
                }
            });
            return false;
        });
    });
</script>
</body>
</html>

