<?php

namespace app\controllers;
use app\models\User;
use Yii;
use yii\web\Response;
use yii\filters\VerbFilter;
class ApiController extends \yii\web\Controller
{
    public function actionRegister()
    {
        $model = new User();
        $username = Yii::$app->request->post('username');
        $password = Yii::$app->request->post('password');
        $model->username = $username;
        $model->password = $password;
//        print_r($model);
        $user = User::find()->where("username='{$username}'")->one();
        if($user){
            echo json_encode(
                [
                    'code' => 0,
                    'message' => '用户已存在!'
                ],JSON_UNESCAPED_UNICODE);
            die;
        }
        $model->save();
        if($model->errors){

            if(@$model->errors['username'][0]){
                echo json_encode(
                    [
                        'code' => 0,
                        'message' => $model->errors['username'][0]
                    ],JSON_UNESCAPED_UNICODE);
                die;
            }
            if(@$model->errors['password'][0]){
                echo json_encode(
                    [
                        'code' => 0,
                        'message' => $model->errors['password'][0]
                    ],JSON_UNESCAPED_UNICODE);
                die;
            }
        }
        echo json_encode(
            [
                'code' => 1,
                'message' => []
            ],JSON_UNESCAPED_UNICODE);
        die;
    }

    public function load($data, $formName = null)
    {
        $scope = $formName === null ? $this->formName() : $formName;
        if ($scope === '' && !empty($data)) {
            $this->setAttributes($data);

            return true;
        } elseif (isset($data[$scope])) {
            $this->setAttributes($data[$scope]);

            return true;
        }

        return false;
    }

    public static function loadMultiple($models, $data, $formName = null)
    {
        if ($formName === null) {
            /* @var $first Model|false */
            $first = reset($models);
            if ($first === false) {
                return false;
            }
            $formName = $first->formName();
        }

        $success = false;
        foreach ($models as $i => $model) {
            /* @var $model Model */
            if ($formName == '') {
                if (!empty($data[$i]) && $model->load($data[$i], '')) {
                    $success = true;
                }
            } elseif (!empty($data[$formName][$i]) && $model->load($data[$formName][$i], '')) {
                $success = true;
            }
        }

        return $success;
    }

}
