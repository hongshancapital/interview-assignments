<?php


use Klein\Exceptions\ValidationException;
use Symfony\Component\Dotenv\Dotenv;
use \PDO;

require_once __DIR__ . '/../vendor/autoload.php';

const PASSWORD_SALT="hello world";

$dotenv = new Dotenv();


$dotenv->load(dirname(__DIR__)."/.env");








$klein = new \Klein\Klein();

//注册输出函数
$klein->app()->register("echo",function() {
	return function($response, $ok, $message) {
		$response->json([
			"ok" => $ok,
			"message" => $message
		]);
	};
});


//注册pdo
$klein->app()->register("pdo",function(){
	$host     = $_ENV["DB_HOST"];
	$name     = $_ENV["DB_NAME"];
	$user     = $_ENV["DB_USER"];
	$password = $_ENV["DB_PASSWORD"];

	$db = null;
	try{
		$db = new \PDO(sprintf("mysql:host=%s;dbname=%s",$host,$name), $user, $password);
	}catch(Exception $e) {

	}
	if(!$db) {
		exit("请仔细配置数据库");
	}
	return $db;
});


/**
 * @title 注册静态页面
 */
$klein->respond("GET","/",function($request, $response, $service, $app) {

	$service->render("../tpl/login.php");
});

/**
 * @title 注册逻辑
 * @body_param userName string
 * @body_param passWord string
 */
$klein->respond("POST","/api/register", function($request, $response, $service, $app){
	
	
	$body = json_decode($request->body(),true);
	$userName = $body["userName"];
	$passWord = $body["passWord"];
	$repeatPassWord = $body["repeatPassWord"];

	//验证表单
	try{
		$service->addValidator("Diy",function($str,$r) {
			if($str != $r) {
				return false;
			}
			$n = 0;
			if(preg_match('/[0-9]/', $str)) {
				$n++;
			}
			if(preg_match('/[a-z]/',$str)) {
				$n++;
			}
			if(preg_match('/[A-Z]/',$str)) {
				$n++;
			}
			if ($n >1) {
				return true;
			}
			return false;
		});

		$service->validate($userName,"用户名只能以英文字母或下划线开头，只能包含英文字母，下划线或数字")->isRegex("/^[a-zA-Z_]([a-zA-Z0-9_])+$/");
		$service->validate($passWord,"密码 长度在 6 位以上 30位以下 不能含有 3 位或以上的连续数字，如 123、234 等 必须有大写字母，小写字母或数字中的两项 ")->isLen(6,30)
		->notRegex("/[0-9]{3}/")
		->isDiy($repeatPassWord);
		
	}catch(ValidationException $e) {
		$app->echo()($response,0,$e->getMessage());
		return;
	}

	//判断用户名是否被占用
	$db = $app->pdo();
	$sth = $db->prepare("select id from user where username = ?");
	$sth->execute([$userName]);
	$user = $sth->fetch();

	if($user) {
		$app->echo()($response,0,"用户名被占用");
		return;
	}

	//
	$sth = $db->prepare("insert into user(username,password,ctime) values (?,?,?)");
	$sth->execute([
		$userName,
		md5(md5($passWord).PASSWORD_SALT),
		time()
	]);
	$id = $db->lastInsertId();

	if($id) {
		$app->echo()($response,1,"注册成功");
	}else{
		$app->echo()($response,0,"注册失败,请联系管理员");
	}
	
});




$klein->dispatch();