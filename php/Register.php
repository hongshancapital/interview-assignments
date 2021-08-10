<?php
namespace app\index\Service;
use app\index\Service\Base;
use app\index\Model\User;
class Register extends Base
{
    /**
     * 用户注册
     * @param $username string 用户名
     * @param $pass   string 密码
     * @param $repass string 重复密码
     * @return array
     */
    public static function register($username, $pass, $repass)
    {
        // 返回格式，一般定义为函数
        $data = array(
            'code' => 200,
            'message' => '注册成功',
            'data' => []
        );

        // 校验用户名
        if (!preg_match("/^[a-zA-Z_][a-zA-Z0-9_]*$/", $username)) {
            $data['code'] = 300;
            $data['message'] = '用户名不合规';
            goto ret;
        }

        // 校验密码长度和是否满足两项
        if (!preg_match("/^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)([^(0-9a-zA-Z)]|[a-z]|[A-Z]|[0-9]){6,}$/", $username)) {
            $data['code'] = 300;
            $data['message'] = '密码长度不够或者不满足两项';
            goto ret;
        }

        // 校验密码是否含有3位以上连续数字
//        $reg = "/(012|123|234|345|456|567|678|789)/";
        if (!preg_match("/(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}\d/", $username)) {
            $data['code'] = 300;
            $data['message'] = '密码含有3位或以上连续数字';
            goto ret;
        }

        // 获取密码加密盐值
        $data['salt'] = Register::getRandChar();
        $data['user_name'] = $username;
        $data['password'] = md5($pass.$data['salt']);
        $data['create_time'] = date('Y-m-d H:i:s', time());

        // 数据插入
        $intRes = User::addUser($data);
        if (!$intRes) {
            $data['code'] = 400;
            $data['message'] = '插入错误';
        }
        ret:
        return $data;

    }

    /**
     * 随机生成要求位数个字符
     * @param length 定长
     */
    public static function getRandChar($length = 16){
        $str = null;
        $strPol = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";//大小写字母以及数字
        $max = strlen($strPol)-1;
        for($i=0;$i<$length;$i++){
            $str.=$strPol[rand(0,$max)];
        }
        return $str;
    }




}
