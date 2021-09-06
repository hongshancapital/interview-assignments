<!DOCTYPE html>
<head>
    <script type="text/javascript" src="http://cdn.staticfile.org/jquery/1.10.0/jquery.min.js"></script>
    <style>
        dt,dd {
            float:left;
        }
        dt {
            text-align:right;
            width:80px;
        }
        dl {
            height:42px;
            line-height:42px;
            clear:left;
        }
        input {
            font-size:18px;
            height:30px;
            line-height:30px;
            padding:5px;
        }
        input[type="submit"] {
            width: 100px;
            height: 40px;
        }
        input[type="reset"] {
            width: 100px;
            height: 40px;
            margin-left: 36px;
        }
    </style>
    <script>
        $(document).ready(function(){
            $('form').submit(function(e){
                e.preventDefault();
                $('.errorMsg').html('');
                $.ajax({
                    method: 'POST',
                    url: "api/register",
                    type: 'post',
                    data: $(this).serialize(),
                    dataType: "json",
                    success: function(r){
                        alert("注册成功");
                    },
                    error: function(r){
                        var data = r.responseJSON;
                        $.each(data.errors, function(k, v){
                            $('input[name="'+k+'"]').next().html(v[0]);
                        })
                    }
	            })
                return false;
            });
        });
    </script>
</head>
<html>
    <body>
        <div>
            <form method="post" action="/api/register">
                @csrf
                <dl><dt>用户名：</dt><dd><input type="text" name="Username"><span class="errorMsg"></span></dd></dl>
                <dl><dt>密码：</dt><dd><input type="password" name="Password"><span class="errorMsg"></span></dd></dl>
                <dl><dt>重复密码：</dt><dd><input type="password" name="RepeatPassword"><span class="errorMsg"></span></dd></dl>
                <dl><dt>&nbsp;</dt><dd><input type="submit" value="提交"><input type="reset" value="重置"></dd></dl>
            </form>
        </div>
    </body>

</html>