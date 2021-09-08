<?php

namespace app\controllers;

use app\models\SignupForm;
use Yii;

class ApiController extends ActiveController
{
    public function accessOptional(){
        return ['register'];
    }

    public function actionRegister()
    {
        try{
            $model = new SignupForm();
            if (!$model->load(Yii::$app->request->post())||!$model->validate()){
                throw new \Exception(current($model->errors)[0]);
            }
            $data=$model;
            return $this->send($data,'','注册成功');
        }catch (\Exception $e){
            return $this->error($e);
        }
    }
}
