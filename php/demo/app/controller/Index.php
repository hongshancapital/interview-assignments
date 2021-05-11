<?php
namespace app\controller;

use app\BaseController;
use think\facade\View;
use think\facade\Request;
use think\exception\ValidateException;
use app\validate\User;
use app\service\UserService;
use app\exception\UserException;

class Index extends BaseController
{
	// 用户注册
    public function index()
    {
    	// 检查是否POST提交
    	if (Request::isPost()) {
    		try {
    			// 验证提交数据
	    		validate(User::class)->check(Request::param());
	    		
	    		// 注册用户
	    		UserService::register(Request::only(['username', 'password']));	    	
	    		return json(['code'=>1, 'msg'=>"Successs"]);
    		} catch (ValidateException $e) {
    			// 验证异常处理
    			return json(['code'=>0, 'msg'=>$e->getMessage()]);
    		} catch (UserException $e) {
    			// 用户模块异常
    			return json(['code'=>0, 'msg'=>$e->getMessage()]);
    		} catch (\Exception $e) {
    			// 未知错误
    			return json(['code'=>0, 'msg'=>"Unkown error" . $e->getMessage()]);
    		}
    	}
    	return View::fetch();
    }
}
