<html>
<head>
    <title>注册</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        *{
            margin: 0 auto;
        }
        body{
            background-color: #343a40;
        }
        .container{
            position: relative;
            top: 100px;
        }
        .news-nav{
            clear: both;
            height: 80px;
            margin-left: 10px;
            margin-right: 10px;
            width: 300px;
            margin: 0 auto;
        }
        .news-nav li{
            float: left;
            list-style-type: none;
            margin: 0 70px;
            font-size: 30px;
            display: block;
            width: 86px;
            height: 79px;
            text-align: center;
            line-height: 79px;
            font-weight: bold;
            color: #007bff;
            cursor: pointer;
        }
        .news-nav li.on{
            color: #74dcff;
            border-bottom-color: #74dcff;
            border-bottom-style: solid;
            border-bottom-width: 2px;
        }

        .modal-dialog{
            max-width: 100% !important;
        }
        .modal-content{
            background:rgba(0,0,0,0.3);
            width: 700px;
        }
        .loginForm{
            width: 400px;
        }
        .loginForm .form-group{
            margin: 30px 0;
        }
        .loginForm .form-group .form-control{
            height: 40px;
            font-size: 15px;
        }
        input[type="checkbox"]{
            position:relative;
            top:-2px;
            vertical-align: middle;
            cursor: pointer;
            zoom:1.6;
        }
        input[type="radio"]{
            position:relative;
            top:-3px;
            vertical-align: middle;
            cursor: pointer;
            zoom:1.6;
        }
        .btn-primary{
            background-color: #3e4963;
            border: 0px solid transparent;
            width: 400px;
            height: 50px;
            font-size: 24px;
            font-family: STKaiti;
        }
        label{
            color: #fff;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="modal-dialog" id="login_form">
        <div class="modal-content">
            <div class="modal-title">
                <ul class="news-nav js-nav-title">
                    <li class="" data="register">注册</li>
                </ul>
            </div>

            <div class="modal-body index-news-list" id="index-news-list-2">
                <form class="loginForm" id="registerForm" action="" method="post">

                    <div class="form-group">
                        <input class="form-control required" value="d1a1da111d" name="name" id="name" type="text" placeholder="请输入要注册的用户名">
                    </div>
                    <div class="form-group">
                        <input class="form-control required" value="sdDAAA11" name="password" id="password" type="password" placeholder="请输入密码">
                    </div>
                    <div class="form-group">
                        <input class="form-control required" value="sdDAAA11" name="rePassword" id="rePassword" type="password" placeholder="请再次输入密码">
                    </div>

                    <div class="form-group">
                        <button class="btn btn-primary" type="button">注册</button>
                    </div>
                    <div id="showTip" style="color: red;font-size: 15px;display: none;"></div>
                </form>
            </div>

        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('.btn-primary').click(function () {
            var name = $('#name').val();
            var password = $('#password').val();
            var rePassword = $('#rePassword').val();
            var username_rep = /^[A-Za-z\-_]\w[A-Za-z0-9_]+$/;
            if(!username_rep.test(name)){
                showTip('用户名只能以英文字母或下划线开头,只能包含英文字母，下划线或数字');
                return false;
            }
            var password_rep = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[\(\)])+$)([^(0-9a-zA-Z)]|[\(\)]|[a-z]|[A-Z]|[0-9]){6,}$/;
            if(!password_rep.test(password)){
                showTip('密码必须有大写字母，小写字母或数字中的两项,并且长度在 6 位以上');
                return false;
            }
            var password_rep1 = /(012|123|234|345|456|567|678|789)/;
            if(password_rep1.test(password)){
                showTip('密码不能含有 3 位以上的连续数字');
                return false;
            }
            if(password != rePassword){
                showTip('两次输入密码不一致');
                return false;
            }
            $.ajax({
                url:'/api/register',
                data:{'username':name,'password':password},
                type:'post',
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showTip(data.message);
                        return false;
                    }
                    if(data.code == 1){
                        alert('注册成功!');
                        return false;
                    }
                }
            })
        });


    });
    function showTip(mgs) {
        $('#showTip').html(mgs);
        $('#showTip').css('display' ,'block');
        setTimeout("$('#showTip').css('display' ,'none')", 2000 )

    }
</script>
</body>
</html>