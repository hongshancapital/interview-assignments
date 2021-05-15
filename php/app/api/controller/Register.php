<?php
declare (strict_types=1);

/**
 * 开发: Atom
 * 时间: 2021-05-15
 */

namespace app\api\controller;


use think\App;
use app\BaseController;
use app\api\validate\User;
use app\middleware\RegisterCheck;
use app\services\User\UserRegisterService;
use think\exception\ValidateException;
use think\facade\Event;

/**
 * 用户注册类
 * Class Register
 * @package app\api\controller
 */
class Register extends BaseController
{
    protected $registerService;
    /**
     * 注册中间件
     * 请求次数规则限制（暂不写）
     * @var array
     */
    protected $middleware = [
        RegisterCheck::class => ['only' => 'index'],
    ];

    public function __construct(UserRegisterService $registerService)
    {
        $this->registerService = $registerService;
    }

    /**
     * 注册方法
     * @return \think\response\Json
     */
    public function index()
    {
        $params = request()->param();
        try {
            //验证器
            validate(User::class)->check([
                'username' => $params['username'],
                'password' => $params['password'],
                'repeat_password' => $params['repeat_password'],
            ]);

            //密码算法（简单写）
            $params['password'] = md5($params['password']);

            //调用服务层
            $res = $this->registerService->create($params);

            //注册事件 用于注册用户属性表和初始化数据（暂不写）
            Event::trigger('UserRegisterEvent', $res);

            return json('', 200, showCode(1006));
        } catch (ValidateException $e) {
            return json('', 202, $e->getError());
        }
    }
}