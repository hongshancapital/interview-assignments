<?php
namespace app\service;

use app\model\User;
use app\exception\UserException;

class UserService {
	/**
	 * 注册用户
	 * 
	 * @param array $data
	 * @throws UserException
	 * @return boolean & integer
	 */
	public static function register($data) {
		try {	
			// 检查用户名是否存在
			if (User::where(['username'=>$data['username']])->count()) {
				throw new UserException("Username is exists");
			}
			
			$user = new User;		
			$data['salt'] = md5(microtime() . uniqid());
			$data['password'] = md5($data['password'] . $data['salt']);
			$data['created'] = time();
			$user->save($data);
			return $user->id;	
		} catch (UserException $e) {
			throw $e;
		}
	}
}