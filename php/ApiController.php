<?php

namespace app\modules\api\modules\v1\controllers;

use Yii;
use yii\filters\VerbFilter;
use yii\filters\ContentNegotiator;
use yii\web\Response;
use yii\data\ActiveDataProvider;
use yii\rest\Controller;
use app\modules\api\common\ErrorCode;
use yii\helpers\ArrayHelper;
use app\modules\api\common\ErrorException;
use app\modules\api\modules\v1\models\User;

/**
 * AuthAssignmentController implements the CRUD actions for Assignment model.
 *
 * @author Misbahul D Munir <misbahuldmunir@gmail.com>
 * @since 1.0
 */
class ApiController extends Controller
{

    public $errorParam;
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        $this->errorParam = new ErrorCode();
        return [
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'index'  => ['GET'],
                    'view'   => ['GET'],
                    'create' => ['GET', 'POST'],
                    'update' => ['GET', 'PUT', 'PATCH'],
                    'delete' => ['GET', 'DELETE'],
                ],
            ],
            'contentNegotiator' => [
                'class' => ContentNegotiator::className(),
                'formats' => [
                    'application/json' => Response::FORMAT_JSON
                ],
            ]
        ];
    }

    protected function verbs()
    {
        return array_merge(parent::verbs(),[
                'get-list' => ['POST'],
                'find-pwd' => ['POST'],
                'login' => ['POST'],
            ]
        );
    }

    public function actions()
    {
        $actions = parent::actions();
        // $actions['index']['prepareDataProvider'] = [$this, 'prepareDataProvider'];
        unset($actions['create'],$actions['update'],$actions['delete']);
        return $actions;
    }

    //注册页面
    public function actionRegister(){
        if(!Yii::$app->request->isPost){
            return  $this->errorParam->paramValidity('仅支持POST方式',1);
        }
        
        $post = Yii::$app->request->post();
        $Username = isset($post['Username']) ? $post['Username'] :"";
        $Password = isset($post['Password']) ? $post['Password'] :"";
        $RepeatPassword = isset($post['RepeatPassword']) ? $post['RepeatPassword'] :"";

        $data=array(
            'Username'=>$Username,
            'Password'=>$Password,
        );
        $mode=new User();

        $result=$mode->UserInfo($data);
        if($result['code'] == 1){
            return  $this->errorParam->paramValidity('error', 1, $result['mesg']);
        }else{
            if($RepeatPassword == true){
                $session = \Yii::$app->session;
                $session->set('user_id',$result['id'],3600*24);
            }
            return  $this->errorParam->paramValidity('success', 0, $result['id']);
        }
        
    }
}
