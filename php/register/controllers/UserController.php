<?php

namespace app\controllers;

use app\models\RegForm;
use app\models\User;
use yii\data\ActiveDataProvider;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * @desc 用户控制器
 */
class UserController extends Controller
{

    /**
     * @desc 用户注册首页
     * @return string
     */
    public function actionIndex()
    {
        $userRegForm = new RegForm();
        if ($this->request->isPost){

        }

        return $this->render('index', [
            'model' => $userRegForm,
        ]);
    }
}
