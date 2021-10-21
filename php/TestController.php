<?php
namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

// laravel 7.2
// 路由:
// Route::post('register', 'TestController@register');
class TestController extends Controller
{

    public function register(Request $request)
    {
        $rule = [
            'Username' => 'required|string',
            'Password' => 'required|min:6|string',
            'RepeatPassword' => 'required|min:6|string',
        ];

        $message = [
            'Username.required' => 'Username 不能为空',
            'Password.required' => 'Password 不能为空',
            'RepeatPassword.required' => 'RepeatPassword 不能为空',
            'Password.min' => 'Password 长度在 6 位以上',
            'RepeatPassword.min' => 'RepeatPassword 长度在 6 位以上'
        ];

        $validator = Validator::make($request->all(), $rule, $message);
        if($validator->fails()){
            return $this->error($validator->errors()->first());
        }

        if(!preg_match('/^[a-zA-Z_]/', $request->Username,$matchFirstLetter)){
            return $this->error("Username 只能以英文字母或下划线开头");
        }

        if(!preg_match('/^[a-zA-Z0-9_]*$/', $request->Username, $matchUsername)){
            return $this->error("Username 只能包含英文字母，下划线或数字");
        }

        if(!preg_match('/^(?![0-9]+$)(?![a-z]+$)(?![A-Zz]+$)[0-9A-Za-z]*$/', $request->Password, $matchPassword)){
            return $this->error("Password 必须有大写字母，小写字母或数字中的两项");
        }

        if(preg_match('/123|234|345|456|567|678|789/', $request->Password, $matchPasswordContinuity)){
            return $this->error("Password 不能含有 3 位或以上的连续数字");
        }

        if($request->Password != $request->RepeatPassword){
            return $this->error("两次输入的密码不一致");
        }

        // 数据存储
        $model = new User();
        if($model::query()->where(['username' => $request->Username])->first()){
            return $this->error("用户名已存在，请更换一个新的");
        }

        $model->create($request->all());

        return $this->success();
    }


    protected function success($data = null, $message = '操作成功', $code = 200){
        return \response()->json(['code' => $code, 'message' => $message, 'data' => $data, 'status' => 'success']);
    }

    protected function error($message = '操作失败', $code = 400){
        return \response()->json(['code' => $code, 'message' => $message, 'status' => 'error']);
    }


}
