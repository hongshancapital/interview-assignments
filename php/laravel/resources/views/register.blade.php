<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>用户注册</title>

        <!-- Fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;600;700&display=swap" rel="stylesheet">

        <!-- Styles -->
        <style>
        </style>

        <style>
            body {
                font-family: 'Nunito', sans-serif;
            }
        </style>
    </head>

    <body>
        <form method="post" action="{{ url('api/register')}}">
            {{csrf_field()}}
            用户名:<input type="text" maxlength="20" name="username"/> <br>
            密码:<input type="password" maxlength="20" name="password"/> <br>
            重复密码:<input type="password" maxlength="20" name="repeat_password"/> <br>
            <input type="submit" value="提交"/>
        </form>
    @if($errors->any())
        <div style="color: red">
            @if(is_object($errors))
                @foreach($errors->all() as $error)
                    <p>{{$error}}</p>
                @endforeach
            @else
                <p>{{$errors}}</p>
            @endif
        </div>
    @endif
    </body>
</html>
