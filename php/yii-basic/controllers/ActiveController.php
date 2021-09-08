<?php

namespace app\controllers;

use Yii;
use yii\base\Exception;
use yii\filters\ContentNegotiator;
use yii\filters\Cors;
use yii\filters\RateLimiter;
use yii\filters\VerbFilter;
use mdm\admin\components\AccessControl;
use yii\filters\auth\CompositeAuth;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\HttpBearerAuth;
use yii\filters\auth\QueryParamAuth;
use yii\helpers\ArrayHelper;
use yii\web\HttpException;
use yii\web\Response;

class ActiveController extends \yii\rest\ActiveController
{

    public $modelClass='\yii\base\Model';


    public function behaviors()
    {
        return [
            'corsFilter'=>[
                'class' => \yii\filters\Cors::className(),
                'cors' => [
                    'Origin' => ['*'],
                    'Access-Control-Request-Method' => ['*'],
                    'Access-Control-Request-Headers' => ['*'],
                    //'Access-Control-Allow-Headers' => ['*'],
                    'Access-Control-Allow-Credentials' => false,
                    'Access-Control-Max-Age' => 3600,
                    'Access-Control-Expose-Headers' => ['X-Pagination-Current-Page'],
                ]
            ],
            'contentNegotiator' => [
                'class' => ContentNegotiator::className(),
                'formats' => [
                    'application/json' => Response::FORMAT_JSON,
                    'application/xml' => Response::FORMAT_XML,
                ],
            ],
            'authenticator'=>[
                'class' => CompositeAuth::className(),
                'optional' => $this->accessOptional(),
                'authMethods' => [
                    HttpBasicAuth::className(),
                    HttpBearerAuth::className(),
                    [
                        'class' => QueryParamAuth::className(),
                        'tokenParam' => 'accessToken',
                    ],
                ],
                'except' => ['options'],
            ],
            'verbFilter' => [
                'class' => VerbFilter::className(),
                'actions' => $this->verbs(),
            ],
            'rateLimiter' => [
                'class' => RateLimiter::className(),
            ],

        ];
    }

    public function actions()
    {
        $actions = parent::actions();
        unset($actions['create'],$actions['delete'],$actions['update'],$actions['view'],$actions['index']);
        return $actions;
    }

    public function verbs(){
        return [
            'index' => ['GET','POST','OPTIONS'],
            'list' => ['GET','POST','OPTIONS'],
            'create' => ['GET','POST','OPTIONS'],
            'update' => ['GET','POST','OPTIONS'],
            'delete' => ['GET','POST','OPTIONS'],
            'view' => ['GET','POST','OPTIONS']
        ];
    }

    protected function send($data=[],$code=200,$message='ok'){
        return ['code'=>$code,'data'=>$data,'message'=>$message];
    }

    protected function error(\Exception $e){
        return ['code'=>$e->getCode(),'data'=>[],'message'=>$e->getMessage()];
    }
}
