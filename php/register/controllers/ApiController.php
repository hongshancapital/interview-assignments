<?php

namespace app\controllers;

use app\models\RegForm;
use app\models\User;
use app\tools\UserRegValidate;
use app\tools\Utility;
use yii\data\ActiveDataProvider;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * @desc 用户控制器
 */
class ApiController extends Controller
{
    public function actionRegister()
    {
        if ($this->request->isPost) {
            $regForm = $this->request->post('RegForm');
            $username = $regForm['Username'];
            $password = $regForm['Password'];
            $passwordRepeat = $regForm['RepeatPassword'];

            $validateUsername = UserRegValidate::isUsername($username);
            if ($validateUsername['code'] != 200) {
                return json_encode($validateUsername);
            }

            $validatePassword = UserRegValidate::isPassword($password);
            if ($validatePassword['code'] != 200) {
                return json_encode($validatePassword);
            }
            if ($password != $passwordRepeat) {
                return json_encode(['code' => 400, 'msg' => '两次密码不一致']);
            }
            $user = User::findOne(['Username' => $username]);
            if ($user) {
                return json_encode(['code' => 400, 'msg' => '请勿重复注册']);
            }
            $user = new User();
            $user->Username = $username;
            $user->Salt = Utility::genRandStr(3, 6);
            $user->Password = md5($password . $user->Salt);
            $user->status = 1;
            $user->created_at = time();
            $user->updated_at = time();
            if ($user->validate()) {
                if ($user->save()) {
                    return json_encode(['code' => 200, 'msg' => '注册成功']);
                }
            }
            return json_encode(['code' => 400, 'msg' => '注册失败']);
        }
    }
}
