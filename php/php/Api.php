<?php
defined('BASEPATH') OR exit('No direct script access allowed');
/**
 * @property register_model $register_model;
 * @property CI_Input $input;
 */
class Api extends CI_Controller {

	public function __construct()
	{
		parent::__construct();
		$this->load->model('register_model', 'register_model');
	}

	public function register()
	{
		if (!$this->input->is_ajax_request()) {
			$this->ajax_return(1, '请求错误');
		}
		$username   = $this->input->post('username', true);
		$password   = $this->input->post('password', true);
		$repassword = $this->input->post('repassword', true);

		// 验证用户名
		if (!preg_match("/^[_a-zA-Z][_a-zA-Z0-9]*$/", $username)) {
			$this->ajax_return(1, '用户名只能是字母下划线开头，包含字母下划线数字');
		}

		if (strlen($username) > 255) {
			$this->ajax_return(1, '用户名超过最大限制');
		}

		// 验证密码
		if (!preg_match("/^[0-9a-zA-Z]{6,}$/", $password)) {
			$this->ajax_return(1, '密码必须是6位以上包含数字大小写字母的字符');
		}
		$p_reg_2 = "/[0-9]/";
		$p_reg_3 = "/[a-z]/";
		$p_reg_4 = "/[A-Z]/";
		if (
			!((preg_match($p_reg_2, $password) && preg_match($p_reg_3, $password)) ||
			(preg_match($p_reg_2, $password) && preg_match($p_reg_4, $password)) ||
			(preg_match($p_reg_3, $password)&& preg_match($p_reg_4, $password)))
		) {
			$this->ajax_return(1, '密码必须含有“小写字母”、“大写字母”、“数字”中的任意两种');
		}
		if (preg_match("/(012|123|234|345|456|678|789)/", $password)) {
			$this->ajax_return(1, '不能含有 3 位或以上的连续数字');
		}
		// 验证确认密码
		if ($password != $repassword) {
			$this->ajax_return(1, '密码与确认密码不一致');
		}

		// 验证用户名是否已存在
		if ($this->register_model->check_exists($username)) {
			$this->ajax_return(1, '用户名已存在');
		}

		// 添加注册用户信息
		if ($this->register_model->add_user(['username' => $username, 'password' => $password])) {
			$this->ajax_return(0, '注册成功');
		}
		$this->ajax_return(1, '注册失败');
	}

	/**
	 * 以json模式返回数据
	 * @param int $code
	 * @param string $msg
	 * @param array $data 需要返回的数据
	 * @return void /json
	 */
	function ajax_return($code = 0, $msg = '', $data = [])
	{
		$json_data = [
			'code' => intval($code),
			'msg'  => strval($msg),
			'data' => $data
		];
		if (!headers_sent()) {
			header("Content-Type: application/json; charset=utf-8");
		}
		echo json_encode($json_data);
		exit();
	}
}
