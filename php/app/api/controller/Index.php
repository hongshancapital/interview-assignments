<?php
declare (strict_types=1);

/**
 * 开发: Atom
 * 时间: 2021-05-15
 */

namespace app\api\controller;


use app\BaseController;

/**
 * 登陆
 * Class Index
 * @package app\api\controller
 */
class Index extends BaseController
{
    public function index(){

        return view('index');
    }
}