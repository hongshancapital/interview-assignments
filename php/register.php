<?php
/**
*
*CREATE TABLE IF NOT EXISTS `user`(
*   `id` int(10) UNSIGNED AUTO_INCREMENT,
*   `username` varchar(30) NOT NULL comment '用户名',
*   `password` char(32) NOT NULL comment '密码',
*   PRIMARY KEY ( `id` )
*)ENGINE=InnoDB DEFAULT CHARSET=utf8;
 * 
 */
try{

    $username = $_POST['username'] ?? null; 
    $password = $_POST['password'] ?? null;
    $rePassword = $_POST['rePassword'] ?? null;
    checkName($username);
    checkPassWord($password,$repassword);

    //入库
    echo json_encode(["msg"=>"注册成功"]);

} catch(Exception $exception){

    echo json_encode(["msg"=>$exception->getMessage()]);
}



function checkName($username) :?RegisterException
{
    if(empty($username)){
        throw new RegisterException("用户名不能为空");
    }
   
    if (!preg_match("/^[_a-zA-Z][a-zA-Z0-9_]*$/i",$username)){
        throw new RegisterException("用户名称首字母必须以英文或者_开头且只能包含英文字母，下划线或数字");
    }
    return null;
}


function checkPassWord($password, $repassword) :?RegisterException
{
    if (empty($password) || empty($repassword)){
        throw new RegisterException("密码或确认密码不能为空");
    }
    
    if ($repassword !== $password) {
        throw new RegisterException("两次密码输入不一致");
    }

    if (!preg_match('/^(?![0-9]+|[a-z]+|[A-Z]+$)\w{6,}$/',$password)) {
        throw new RegisterException(" 必须有大写字母，小写字母或数字中的两项,且长度必须在6位以上");
    }

    if (preg_match('/[\w*][0-9]{3,}[\w]*/', $password)) {
        throw new RegisterException("不能含有 3 位以上的连续数字");
    }
    
    return null;
}



class RegisterException implements Exception{

}

