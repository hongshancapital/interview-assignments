<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;
use mysql_xdevapi\Exception;

class RegisterController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    /**
     * 注册功能
     *
     * @param  \Illuminate\Http\Request  $request
     */
    function register(Request $request)
    {
        //用户名规则：只能以英文字母或下划线开头，只能包含英文字母，下划线或数字
        //密码规则：长度在6位以上，不能含有3位或以上的连续数字，如 123、234 等，必须有大写字母，小写字母或数字中的两项
        $params = $this->validate($request, [
            'username' => 'required|regex:/^[a-zA-Z_][0-9a-zA-Z_]{1,}$/',
            'password' => [
                'required',
                'min:6',
                'regex:/^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[A-Za-z0-9]{1,}$/',
            ],
            'repeat_password' => 'required'
        ]);

        $username = $request->post('username');
        $password = $request->post('password');
        $repeatPassword = $request->post('repeat_password');

        // 密码不能出现数字连续
        $ret = '/^(?:(\\d)(?!((?<=9)8|(?<=8)7|(?<=7)6|(?<=6)5|(?<=5)4|(?<=4)3|(?<=3)2|(?<=2)1|(?<=1)0){5})(?!\1{5})(?!((?<=0)1|(?<=1)2|(?<=2)3|(?<=3)4|(?<=4)5|(?<=5)6|(?<=6)7|(?<=7)8|(?<=8)9){5})){3}$/';

        if (!preg_match($ret, $password, $res)) {
            echo '密码不能是3位以上的连续数字';exit;
        }

        $data = [
            $username,
            $password,
            time(),
            time()
        ];

        try {
            $res = DB::insert('insert into users (username,passwd,created_at, updated_at) values (?,?,?,?)',
                $data);

            var_dump($res);
        } catch (\Throwable $e) {
            Log::error("DB error:" . $e->getMessage());
            throw $e;
        }

        echo '注册成功<br>';
        echo "Username: $username <br> Password: $password <br> Repeat_passsord: $repeatPassword";
    }
}
