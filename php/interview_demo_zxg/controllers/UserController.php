<?php

namespace app\controllers;

use app\common\response\StatusCode;
use app\models\User;
use app\validates\RegisterForm;
use Yii;
use app\common\BaseApiController;
use app\common\response\JsonResult;

class UserController extends BaseApiController
{

    /**
     * 用户注册
     * @return \yii\console\Response|\yii\web\Response
     */
    public function actionRegister()
    {
        if (Yii::$app->request->getIsGet()) {
            return JsonResult::error(StatusCode::$error);
        }

        $post = Yii::$app->request->post();

        $form = new RegisterForm();
        $form->setAttributes($post, false);
        if (!$form->validate()) {
            $code = $form->valid_code ?: StatusCode::$invalid_param;
            return JsonResult::error($code);
        }

        /** @var $userModel User */
        $userModel = new User();
        $userModel->setAttributes($form->attributes);
        $userModel->generatePassword();
        if (!$userModel->save()) {
            return JsonResult::error(StatusCode::$error, '注册失败');
        }

        return JsonResult::success();
    }

}
