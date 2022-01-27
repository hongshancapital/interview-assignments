<?php
namespace app\index\controller;
use app\index\controller\Base;
use app\index\Service\Register;
class User extends Base
{
    public function register()
    {
        $username = input('username');
        $pass = input('password');
        $repass = input('repassword');
        $data = array(
            'code' => 200,
            'message' => '注册成功',
            'data' => []
        );
        if (empty($username) || empty($repass) || empty($pass)) {
            $data['code'] = 300;
            $data['message'] = '用户名密不能为空';
            return $data;
        }
        // 判断密码是否一致
        if ($pass != $repass) {
            $data['code'] = 300;
            $data['message'] = '两次密码不一字';
            return $data;
        }
        // 数据处理以及验证
        $res = Register::register($username, $pass, $repass);
        if ($res['code'] == 200) {
            return $data;
        } else {
            return $res;
        }


}
