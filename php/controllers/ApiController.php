<?php

namespace app\controllers;

use Yii;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\Response;
use app\models\User;

class ApiController extends Controller
{
    // public $enableCsrfValidation=false;

    /**
     * Displays homepage.
     *
     * @return string
     */

    public function actionRegister()
    {
        $postData = Yii::$app->request->post();
        // print_r($postData);
        // exit;
        $reData = array(
            "rs" => 0,
            "msg" => "成功",
        );
        $reCode = "1";
        if (!preg_match('/^[a-zA-Z_][a-zA-Z0-9_]*$/', $postData['username']))
        {
            $reData['msg'] = "用户名以字母下划线开头，只能包含字母下划线或数字";
            return json_encode($reData);
        }
        $pNum = 0;
        if (preg_match('/\d/', $postData['p']))
            $pNum++;
        if (preg_match('/[a-z]/', $postData['p']))
            $pNum++;
        if (preg_match('/[A-Z]/', $postData['p']))
            $pNum++;
        if (preg_match('/\d{3}/', $postData['p']) || $pNum < 2 || !preg_match('/^.{6,}$/', $postData['p']))
        {
            $reData['msg'] = "密码长度6位以上，不能含有3位以上连续数字，必须含有大写字母 小写字母 数字中的两项";
            return json_encode($reData);
        }
        if($postData['p'] != $postData['r_p'])
        {
            $reData['msg'] = "密码不同";
            return json_encode($reData);
        }

        $user = new User();
        $postData['p'] = md5($postData['p']);
        unset($postData['r_p']);
        $user->regUser($postData);
        $reData['rs'] = 1;
        return json_encode($reData);
    }
}
