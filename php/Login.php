<?php
namespace app\index\controller;

use \think\Controller;
use think\Validate;

class Login extends Controller
{
    public function index()
    {
        return $this->fetch();
    }

    public function register()
    {
        $data = $this->request->post();
        $validate = new Validate();

        $validate->rule([
                'Username'=>function($value) { 
                    return  preg_match('/^[a-zA-Z_]([a-zA-Z0-9_])+]?/',$value) ? true : '用户名不符合规则';
                },
                'Password'=>function($value) {
                  
                    $status = preg_match('/^(?=[0-9a-zA-Z]*[a-z])(?=[0-9a-zA-Z]*[A-Z])(?=[0-9a-zA-Z]*\d)[0-9a-zA-Z]{6,100}$/',$value) ? true : '密码应包含大小写数字';
                   
                    if(strlen($value) <= 5 || empty($value) || strlen($value) > 1000 ) return '密码不符合规则';
                    $j = 1;
                    /**次数连续 */
                    for($i =0; $i<strlen($value); $i++) {
                        if(is_numeric($value[$i]) ){
                            if(isset($value[$i+1]) && $value[$i] + 1 == $value[$i+1] ) {
                                $j++;
                            }else{
                                $j = 1;
                            }
                        }
                        if($j >= 3){
                            return '密码不符合规则';
                        }
                    }
        
                    return $status;
                }

        ]);

        if (!$validate->check($data)) {
            return $this->jsonSuccess($validate->getError());
        }

        return $this->jsonSuccess("注册成功");
       
    }


    /**
	 * 成功返回
	 * @param $msg
	 * @param int $code
	 * @param array $data
	 */
    function jsonSuccess($data = [], $msg = 'ok', $code = 0) {
		header('Content-Type:application/json, charset=utf-8');
		echo json_encode(['code' => $code, 'msg' => $msg, 'data' => $data], JSON_UNESCAPED_UNICODE);
		exit;
	}


	/**
	 * 失败返回
	 * @param $msg
	 * @param int $code
	 * @param array $data
	 */
	function jsonError($msg, $code = 1, $data = []) {
		header('Content-Type:application/json, charset=utf-8');
		echo json_encode(['code' => $code, 'msg' => $msg, 'data' => $data], JSON_UNESCAPED_UNICODE);
		exit;
	}

}
