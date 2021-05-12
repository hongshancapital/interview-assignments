<?php
/**
 * User: xg
 * Date: 2021/5/11
 */
namespace app\common\response;

use Yii;

/**
 * api json格式返回
 * Class JsonResult
 * @package app\common\response
 */
class JsonResult {

    public static $data = [];

    public static function error(array $code, $msg = '') {
        return self::build($code, $msg);
    }

    public static function success($data = NULL) {
        if ($data == NULL) {
            $data = new \stdClass();
        }
        return self::build(StatusCode::$success, '', $data);
    }

    public static function build(array $code, $msg = '', $data = NULL) {
        self::$data['code'] = $code['code'];
        self::$data['msg'] = empty($msg) ? $code['msg'] : $msg;
        if ($data !== NULL) {
            self::$data['data'] = $data;
        } else {
            self::$data['data'] = new \stdClass();
        }
        return self::ajaxReturn();
    }

    private static function ajaxReturn() {
        $response = Yii::$app->getResponse();
        $response->format = 'json';
        $response->data = self::$data;
        return $response;
    }
}