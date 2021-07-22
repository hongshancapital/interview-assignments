<?php


namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Model\User;

class UserController extends Controller
{
    public $pwdPrefix = '^%$#@&*UY';

    

    public function register(Request $request)
    {
        $userName = $request->input('Username');
        $password = $request->input('Password');
        $repeatPassword = $request->input('RepeatPassword');

        if (empty($userName)) {
            return $this->error('Username必填');
        }
        if (empty($password)) {
            return $this->error('Password必填');
        }
        if ($password != $repeatPassword) {
            return $this->error('请检查密码是否一致');
        }

        if (preg_match('/\W/', $userName)) {
            return $this->error('Username只能由字母数字和下划线组成');
        }

        if (preg_match('/^\d+/', $userName)) {
            return $this->error('Username不能以数字开头');
        }

        if (strlen($password) <= 6) {
            return $this->error('Password长度在6位以上');
        }

        if (preg_match('/\d{4,}/', $password)) {
            return $this->error('不能含有3位以上的连续数字');
        }

        // 对特殊字符不起作用
        // if (!preg_match('/^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$){7,}/', $password)) {
        //     return $this->error('必须有大写字母，小写字母或数字中的两项');
        // }

        $num = 0;
        if (preg_match('/[0-9]+/', $password)) {
            $num++;
        }
        if (preg_match('/[a-z]+/', $password)) {
            $num++;
        }
        if (preg_match('/[A-Z]+/', $password)) {
            $num++;
        }
        if ($num < 2) {
            return $this->error('必须有大写字母，小写字母或数字中的两项');
        }

        $user = User::where('account', $userName)->first();
        if (empty($user)) {
            $pwd = md5($this->pwdPrefix . $password);
            User::insert(array('account' => $userName, 'pwd' => $pwd));
            return $this->success([]);
        } else {
            return $this->error('用户账号不能重复');
        }

        return $this->success([]);
    }
}
