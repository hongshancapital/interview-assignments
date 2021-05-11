<?php
namespace app\validate;

use think\Validate;

class User extends Validate {
	protected $rule = [
		'username' => ['require', 'min'=>6, 'max' => 100, 'regex' => '/^[a-z|A-Z|_][a-z|A-Z|\d|_]+/'],
		'password' => '_checkPassword',
		'repeat_password' => 'require',
	];
	
	protected $message = [
		'username'  =>  'Username is invalid',
		'password' =>  'Password is invalid',
	];
	
	// 检查密码
	protected function _checkPassword($password, $rules, $data) {
		// 检查是否匹配
		if ($data['password'] != $data['repeat_password']) {
			return "Password is not match";
		}
		
		/*
		 * 6位以上
		 * 必须有大写字母，小写字母或数字中的两项
		 * 不能含有 3 位以上的连续数字
		 */
		if (!preg_match_all("/(?!^[a-z]+$)(?!^[A-Z]+$)(?!^[\d]+$)(?!^[^a-zA-Z\d]+$)^.{6,}$/", $password, $result) 
			|| preg_match("/[\w]*[\d]{3,}[\w]*/", $password)) {
			return "Password is invalid";
		}	
		return true;
	}
}