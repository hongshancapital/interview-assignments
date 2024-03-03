<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户注册</title>

    <link rel="stylesheet" href="{{asset('static/js/layui/css/layui.css')}}" media="all">

    <style>
        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }

        .container {
            margin: 0 auto;
            width: 500px;
            height: 500px;
            border: 1px solid #ccc;
            position: relative;
            top: 50%;
            margin-top: -250px;
        }

        .form {
            padding: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="form">
        <form class="layui-form" action="{{url('user/register')}}" method="post">
            <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input type="text" name="username" lay-verify="username" autocomplete="off" placeholder="请输入用户名"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-block">
                    <input type="password" name="password" lay-verify="password" autocomplete="off" placeholder="请输入密码"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-block">
                    <input type="password" name="password_confirmation" lay-verify="password_confirmation"
                           autocomplete="off"
                           placeholder="请输入密码"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    {{csrf_field()}}
                    <button type="submit" class="layui-btn" lay-submit="" lay-filter="form">注册</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="{{asset('static/js/layui/layui.js')}}"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form;

        form.on('submit(form)', function (data) {
            data = data.field;

            if (data.username.length <= 0) {
                layer.msg('请输入用户名');
                return false;
            }

            if (!new RegExp("[a-zA-Z0-9_]+$").test(data.username)) {
                layer.msg('用户名格式不正确');
                return false;
            }

            if (data.password.length <= 0) {
                layer.msg('请输入密码');
                return false;
            }

            if (data.password.length < 6) {
                layer.msg('密码长度在6位以上');
                return false;
            }

            if (new RegExp("123(?:4(?:5(?:6(?:7(?:89?)?)?)?)?)?|234(?:5(?:6(?:7(?:89?)?)?)?)?|345(?:6(?:7(?:89?)?)?)?|456(?:7(?:89?)?)?|567(?:89?)?|6789?|789").test(data.password)) {
                console.log('密码格式不正确，出现重复数字');
                layer.msg('密码格式不正确');
                return false;
            }

            if (!new RegExp("[a-z|A-Z][0-9]+$").test(data.password)) {
                console.log('密码格式不正确，必须小写大写数字');
                layer.msg('密码格式不正确');
                return false;
            }

            if (data.password_confirmation.length <= 0) {
                layer.msg('请输入确认密码');
                return false;
            }

            if (data.password !== data.password_confirmation) {
                layer.msg('两次密码不一致');
                return false;
            }

            return true;
        });
    });
</script>

<script>
    @if (count($errors) > 0)
            @foreach ($errors->all() as $error)
layer.msg('{{$error}}');
    @endforeach
    @endif
</script>

</body>
</html>
