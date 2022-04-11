<?php
/**
 * Created by PhpStorm.
 * User: maping
 * Date: 2021/8/5
 * Time: 上午10:49
 */

namespace api;
use model\UserModel;
class ApiController extends Controller
{
    public function register($request)
    {
        $params = $request->post;
        if (!isset($params['username']) || !isset($params['password']))
        {
            return $this->returnError('请完善参数');
        }
        //1.检测用户名
        $username = $this->checkoutUsername($params['username']);
        if ($username != $params['username'])
        {
            return $this->returnError($username);
        }
        //2.检测密码
        $password = $this->checkoutPassword($params['password']);
        if ($password != $params['password'])
        {
            return $this->returnError($password);
        }
        //3.插入数据库
        if (!empty(UserModel::getInstance()->getUser($username)))
        {
            return $this->returnError('抱歉，该用户名已存在');
        };
        try{
            UserModel::getInstance()->addUser($username,md5($password));
            return $this->returnSuccess([],'恭喜您，注册成功');
        }catch (\Exception $exception)
        {
            return $this->returnError('抱歉，注册失败');

        }

    }

    public function checkoutUsername($username)
    {
        $first = substr($username,0,1);
        if (!preg_match("/^[a-zA-Z\._]+$/",$first))
        {
           return '抱歉，用户名只能以英文字母或下划线开头';
        }

       if (!preg_match("/^[a-zA-Z0-9\._]+$/",$username))
       {
          return '抱歉，用户名只能包含英文字母，下划线或数字';
       }
       return $username;
    }

    public function checkoutPassword($password)
    {
        if (strlen($password) <= 6)
        {
            return '抱歉，密码长度必须在六位以上';
        }

        if (preg_match("/(0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){2}\d/", $password, $matches) > 0 ){
            return '抱歉，密码不能包含三位以上连续数字';
        }
        if (preg_match("/^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[\(\)])+$)([^(0-9a-zA-Z)]|[\(\)]|[a-z]|[A-Z]|[0-9]){6,}$/",$password,$matches) == 0)
        {
            return '必须有大写字母，小写字母或数字中的两项';
        }

        return $password;

    }
}