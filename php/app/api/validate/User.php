<?php

namespace app\api\validate;
/**
 * 开发: Atom
 * 时间: 2021-05-15
 */

use think\Validate;

class User extends Validate
{
    //规则
    protected $rule = [
        'username' => 'require|checkName:',
        'password' => 'require|min:6|confirm:repeat_password|checkPassword:',
    ];

    //提示信息
    protected $message = [
        'username.require' => '用户名不能为空',
        'password.require' => '密码不能为空',
        'password.min' => '密码不能少于6位',
        'password.confirm' => '两次密码不一致',
    ];

    /**
     * 自定义用户名规则
     * @param $value
     * @return bool|string
     */
    protected function checkName($value)
    {
        return (preg_match('/^[a-z|A-Z|_][a-z|A-Z|\d|_]+/', $value)) ? true : '只能以英文字母或下划线开头';

    }

    /**
     * 自定义密码规则
     * @param $value
     * @return string
     */
    protected function checkPassword($value)
    {
        //三位连续数字
        if (preg_match('/[\w]*[\d]{3,}[\w]*/', $value)){
            return '密码中不能有三位连续数字';
        }
        //大写字母,小写字母或数字中的两项
        if (!preg_match('/^(?!([a-zA-Z]+|\d+)$)[a-zA-Z\d]+/s', $value)){
            return '大写字母,小写字母或数字中的两项';
        }

        return true;

    }

}