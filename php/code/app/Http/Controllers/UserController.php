<?php
/*
 * 用户注册
 * @author jzy
 */

namespace App\Http\Controllers;

use App\Models\User;
use \Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class UserController extends Controller
{
    /*
     * 用户注册
     * @author jzy
     * @params['username'] 用户名
     * @params['password'] 密码
     * @params['confirm_password'] 确认密码
     */
    public function register(Request $request)
    {
        $params = [
            'username' => $request->post('username'),
            'password' => $request->post('password'),
            'password_confirmation' => $request->post('password_confirmation')
        ];

        $rules = [
            'username' => 'required|regex:/[a-zA-Z0-9_]+$/',
            'password' => 'required|confirmed',
            'password_confirmation' => 'required|same:password'
        ];

        $messages = [
            'username.required' => '请输入用户名',
            'username.regex' => '用户名格式不正确',
            'password.required' => '请输入用户名',
            'password.regex' => '密码格式不正确',
            'password_confirmation.required' => '请输入确认密码',
            'password.confirmed' => '两次密码不一致'
        ];


        $validator = Validator::make($params, $rules, $messages);

        if ($validator->fails()) {
            $msg = '';

            foreach ($validator->errors()->messages() as $error) {
                $msg = $error[0];
            }

            return back()->withErrors($msg);
        }


        $model = new User();

        $user = $model->where('name', $params['username'])->first();

        if ($user) {
            return back()->withErrors('用户已存在');
        }

        $result = User::create([
            'name' => $params['username'],
            'password' => Hash::make($params['password']),
            'email' => '1501877362@qq.com'
        ]);

        if (!$result) {
            return back()->withErrors('注册失败');
        }

        return back()->withErrors('注册成功');
    }
}