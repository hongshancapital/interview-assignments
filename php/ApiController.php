<?php
/**
 * Created by PhpStorm.
 * User: 郭宏进
 * Date: 2021/6/4
 * Time: 上午10:48
 */
namespace App\Http\Controllers\Api;


use App\Common\ResponseCode;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class ApiController extends Controller
{
    public function index(Request $request)
    {
        $rule = [
            'username' => 'required|string',
            'password' => 'required|string',
            'repeat_password' => 'required|string|same:password',
        ];
        $this->_validate($request, $rule,['用户名称必填','密码必填','确认密码必填']);

        $userName = $request->username;
        $password = $request->password;

        $this->checkUserName($userName);
        $this->checkPassWord($password);

        $userModel = new UserModel();

        $userModel->username= $userName;
        $userModel->password = md5($password);
        $userModel->save();
        return $this->_response($userModel);
    }

    private function checkUserName(string $userName)
    {
        if (preg_match('/^\d+/', $userName)) {
            $message = '用户名不能以数字开头';
            throw new \Exception($message, ResponseCode::USERNAME_ERROR);
        }

        if (preg_match('/\W/', $userName)) {
            $message = '用户名只能由字母数字和下划线组成';
            throw new \Exception($message, ResponseCode::USERNAME_ERROR);
        }

    }

    private function checkPassWord($password)
    {
        if (strlen($password) <= 6) {
            $message = '密码长度在6位以上';
            throw new \Exception($message, ResponseCode::PASSWORD_ERROR);
        }
        if (preg_match('/\d{4,}/', $password)) {
            $message = '密码不能含有3位以上的连续数字';
            throw new \Exception($message, ResponseCode::PASSWORD_ERROR);
        }
    }
}
