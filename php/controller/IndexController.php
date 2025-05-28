<?php
/**
 * Created by PhpStorm.
 * Date: 2021/8/9
 * Time: 15:01
 */

class IndexController extends Controller
{
    //数据表
    protected $sql = "CREATE TABLE `rg_users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(80) NOT NULL DEFAULT '' COMMENT 'Username',
  `pass_word` char(32) NOT NULL DEFAULT '' COMMENT 'Password',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

    //注册页面
    public function actionRegisterPage()
    {
        $this->layout = false;
        $this->render('register_page');
    }

    public function actionRegister()
    {
        $userName = Yii::app()->request->getParam('userName');
        $password = Yii::app()->request->getParam('password');
        $repeatPassword = Yii::app()->request->getParam('repeatPassword');

        $msg = '';
        if (!$this->checkUserName($userName)) {
            $msg = "只能以英文字母或下划线开头,只能包含英文字母，下划线或数字";
        }

        if (!$this->checkPassword($password)) {
            $msg = "长度在 6 位以上，不能含有 3 位以上的连续数字，必须有大写字母，小写字母或数字中的两项 ";
        }

        if (!$this->checkRepeatPassword($password, $repeatPassword)) {
            $msg = "两次密码不一致或密码为空";
        }
        if (!empty($msg)) {
            $this->outData(200, $msg);
        }

        $RgUsers = new RgUsers();
        $RgUsers->user_name = $userName;
        $RgUsers->pass_word = md5($password);
        if ($RgUsers->save()) {
            $this->outData(200, '注册成功');
        } else {
            $this->outData(200, '注册失败');
        }
    }

    public function outData($code = 200, $msg = '', $data = [])
    {
        echo json_encode([
            'code' => $code,
            'msg' => $msg,
            'data' => $data,
        ]);
        Yii::app()->end();
    }

    public function checkUserName($userName)
    {
        if (preg_match('/^[a-zA-Z_][a-zA-Z0-9_]*$/', $userName))
            return true;
        else
            return false;
    }

    public function checkPassword($password)
    {
        if (preg_match('/^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$).{6,}$/', $password) && preg_match('/^\d{3}$/', $password))
            return true;
        else
            return false;
    }

    public function checkRepeatPassword($password, $repeatPassword)
    {
        if ($password != $repeatPassword)
            return false;
        else
            return true;
    }

}