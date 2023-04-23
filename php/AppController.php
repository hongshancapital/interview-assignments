<?php
namespace app\core;

use yii;
use yii\web\Controller;
use app\libs\ApiCodeConst;

class AppController extends Controller
{

    /**
     * @var object Yii $app对象
     */
    protected $app;

    /**
     * @var array get数组
     */
    protected $get;

    /**
     * @var array post数组
     */
    protected $post;

    /**
     * @var object Yii redis连接对象
     */
    protected $redis;

    /**
     * @var array 不需要验签的接口地址(地址不加版本号) [“tim-back/tim”]
     */
    private static $pathNoCheck = [

    ];

    /**
     * @var bool 是否进行crsf验证
     */
    public $enableCsrfValidation = false;

    public function init()
    {

        $this->app = Yii::$app;
        $this->get = Yii::$app->request->get();
        $this->post = Yii::$app->request->post();
        $this->redis = Yii::$app->redis;
        if (!defined('IS_CHECK_SIGN') || IS_CHECK_SIGN) {
            $this->checkSign();
        }
    }

    /*
     * 验证签名
     * */
    private function checkSign()
    {
        $methodData = [];
        if (Yii::$app->request->isGet) {
            $methodData = $this->get;
        } else if (Yii::$app->request->isPost) {
            $methodData = $this->post;
        }

        if (empty($methodData)) {
            $this->response(102, ['msg' => '请求错误'], '请求错误');
        }

        $mp_sign_tmp = isset($methodData['sign_str']) ? $methodData['sign_str'] :'';

        // 删除多余参数
        unset($methodData['s']);
        unset($methodData['sign_str']);

        // 参数排序
        ksort($methodData);
        $str = '';
        foreach ($methodData as $k => $v) {
            $str .= $k . '=' . $v . "&";
        }
        $str .= 'secret_key=' . API_SERCET_KEY;

        //加密比对
        $mp_sign = sha1($str, false);
        if ($mp_sign != $mp_sign_tmp) {
            $this->response(101, ['msg' => '验签错误'], '验签错误');
        }
    }

    /**
     * API 响应结果
     * @param int $code
     * @param array $response_data
     * @param string $msg
     */
    public function response ($code, $response_data = array(), $msg = '')
    {
        $response_data	= ["data" => $response_data];

        $code = intval($code);
        $code_list = ApiCodeConst::$codesInfo;
        $result = array('status' => $code);
        $result = array_merge($result, array('msg' => $code_list[$code]));

        if (!empty($msg)) {
            $result['msg'] = $msg;
        }
        $result['msg'] = str_replace(PHP_EOL, '', $result['msg']);
        echo  !empty($response_data) ? json_encode(array_merge($result, $response_data)) : json_encode($result);exit();
    }

    /*
     * API 提交redis锁，防止重复提交
     * @params  lock_key 缓存key
     * */
    public function checkResubmit($lock_key, $lock_expire = 5)
    {
        $is_lock = $this->redis->setnx($lock_key, 1); // 加锁
        if($is_lock == true){ // 获取锁权限
            $this->redis->setex($lock_key, $lock_expire, '1'); // 写入内容
            // 释放锁
            $this->redis->del($lock_key);
            return true;
        }else{
            // 防止死锁
            if($this->redis->ttl($lock_key) == -1){
                $this->redis->expire($lock_key, $lock_expire);
            }
            return false; // 获取不到锁权限，直接返回
        }
    }
}