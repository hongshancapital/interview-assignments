<?php

namespace app;

use origin\Request;
use origin\Response;
use app\module\User;

class Register
{
    private $Request;
    private $params;
    private $UserModule;

    /**
     * 构造
     */
    public function __construct()
    {
        $this->Request = new Request();
        $this->UserModule = new User();
    }

    /**
     * 服务启动
     * @author decezz@qq.com
     * @return void
     */
    public function run()
    {
        $this->prepareParams();
        $this->params['password'] = md5($this->params['password']);
        $result = $this->UserModule->addUser($this->params);
        if ($result) {
            $this->response(200, 'add user success');
        }
        $this->response(400, 'add user fail');
    }

    /**
     * 响应
     * @author decezz@qq.com
     * @param int $code
     * @param string $content
     * @return mixed
     */
    private function response(int $code, string $content)
    {
        (new Response())->code($code)->content($content)->send();
    }

    /**
     * 准备参数
     * @author decezz@qq.com
     * @return void
     */
    private function prepareParams()
    {
        $post = $this->Request->post;

        // 只能以英文字母或下划线开头
        // 只能包含英文字母，下划线或数字
        if (!isset($post['Username']) ||
            strlen($post['Username']) > 32 ||
            !preg_match('/^[A-Za-z_]{1}/', $post['Username']) ||
            preg_match('/[\W]{1}/', $post['Username'])) {
            $this->response(400, 'username error');
        }
        // 必须有大写字母，小写字母或数字中的两项
        $number = preg_match('/[\d]{1}/', $post['Password']);
        $lower = preg_match('/[a-z]{1}/', $post['Password']);
        $upper = preg_match('/[A-Z]{1}/', $post['Password']);
        if (($number + $lower + $upper) < 2) {
            $this->response(400, 'must have two of upper case, lower case or number');
        }
        // 长度在 6 位以上
        // 不能含有 3 位以上的连续数字
        if (!isset($post['Password']) ||
            strlen($post['Password']) <= 6 ||
            strlen($post['Password']) > 32 ||
            preg_match('/[\d]{3}/', $post['Password'])) {
            $this->response(400, 'password error');
        }
        if (!isset($post['RepeatPassword']) || $post['Password'] != $post['RepeatPassword']) {
            $this->response(400, 'repeat password error');
        }
        $this->params = [
            'username'  => $post['Username'],
            'password'  => $post['Password'],
        ];
    }
}
