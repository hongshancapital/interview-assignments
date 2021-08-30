<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Index extends CI_Controller {

	public function __construct()
	{
		parent::__construct();
	}

	/**
	 * 注册页
	 */
	public function index()
	{
		$this->load->view('register');
	}
}
