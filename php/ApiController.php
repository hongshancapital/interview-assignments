<?php
namespace app\controllers;

use yii;
use app\core\AppController;
use app\models\YaValidate;
use app\models\User;

class ApiController extends AppController
{
    public function init()
    {
        parent::init();
    }

    public function actionRegister ()
    {
        try {
            $data = YaValidate::getInstance()->check([
                ['user_name', 'required', 'message'=>'用户名称不能为空'],
                ['password', 'required', 'message'=>'密码不能为空'],
                ['reset_password','required', 'message'=>'密码不能为空'],
            ], $this->post);

        } catch (\Exception $e) {
            $this->response(1, ['msg' => $e->getMessage()]);
        }

        // 防止并发
        $lockKey = CACHE_SYNC_LOCK . md5(__CLASS__ . __FUNCTION__ . json_encode($data));
        if (!$this->checkResubmit($lockKey)) {
            $this->response(600, ['msg' => '亲，正在处理中请稍后...']);
        }

        $this->checkValiData($data);

        $comment = new User();
        if ($comment->check_user_name($data['user_name'])) {
            $this->response(1, ['msg' => '该用户已注册...', 'data' => []]);
        }
        $rest = $comment->add($data);
        if (empty($rest)) {
            $this->response(1, ['msg' => '用户注册失败...', 'data' => []]);
        }

        $this->response(0, ['msg' => '注册成功', 'data' => $rest]);
    }

    private function checkValiData ($data)
    {
        // 验证用户名称  只能以英文字母或下划线开头  只能包含英文字母，下划线或数字
        if (!preg_match('/^[a-zA-Z_]$/', substr($data['user_name'], 0, 1))) {
            $this->response(1, ['msg' => '用户名只能以英文字母或下划线开头']);
        }
        if (!preg_match('/^[a-zA-Z_][a-zA-Z0-9_]*$/', $data['user_name'])) {
            $this->response(1, ['msg' => '用户名只能包含英文字母，下划线或数字']);
        }

        // 验证用户密码 1：长度在 6 位以上 2：不能含有 3 位以上的连续数字 3：必须有大写字母，小写字母或数字中的两项
        if ($data['password'] != $data['reset_password']) {
            $this->response(1, ['msg' => '两次输入密码不一致']);
        }
        if (strlen($data['password']) <= 6) {
            $this->response(1, ['msg' => '密码长度在6位数以上']);
        }
        if (preg_match("/\d{3}/", $data['password'], $matches) > 0) {
            $this->response(1, ['msg' => '密码不能含有3位以上的连续数字']);
        }
        if (!preg_match('/^(.*[A-Za-z].*\d.*|.*\d.*[A-Za-z].*|.*[A-Z].*[a-z].*|.*[a-z].*[A-Z].*)$/', $data['password'])) {
            $this->response(1, ['msg' => '必须有大写字母，小写字母或数字中的两项']);
        }
    }
}