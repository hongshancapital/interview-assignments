<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class register_model extends CI_Model {

	private $db = null;
	const REG_PREFIX_KEY = "45b9c3a7";

	public function __construct()
	{
		parent::__construct();
		$this->db = $this->load->database("default", TRUE);
	}

	/**
	 * 查询用户名是否已存在
	 * @param $username
	 * @return mixed
	 */
	public function check_exists($username)
	{
		return $this->db->where('username', $username)->count_all_results('user');
	}

	/**
	 * 添加注册用户
	 * @param $data array
	 * @return bool
	 */
	public function add_user($data)
	{
		$data['salt'] = $this->_get_salt();
		$data['password'] = md5(self::REG_PREFIX_KEY . $data['password'] . $data['salt']);
		$data['status'] = 1;
		$data['create_time'] = date('Y-m-d H:i:s');
		return $this->db->insert('user', $data);
	}

	/**
	 * 生成随机salt值
	 * @return string
	 */
	private function _get_salt()
	{
		$code_round = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
		$salt = "";
		for($i = 0; $i < 6; $i++){
			$salt .= $code_round[mt_rand(0, 61)];
		}
		return $salt;
	}
}
