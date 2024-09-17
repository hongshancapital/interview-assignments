<?php

namespace app\controllers;

use app\models\SignupForm;
use Yii;
use yii\web\Controller;

class SiteController extends Controller
{
    public function actionIndex()
    {
        $model = new SignupForm();
        if ($model->load(Yii::$app->request->post())) {
            $model->validate();
        }

        return $this->render('signup', [
            'model' => $model,
        ]);
    }
}
