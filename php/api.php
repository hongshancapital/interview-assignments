<?php
/***
 * 模拟的一个路由文件
 * 
 * @author Beyond<xc_beyond@163.com>
 * @copyright 2022-9-13
 */
    
$routeArr = explode('/', substr($_SERVER['REQUEST_URI'],1 ));
if(empty($routeArr[1]) || is_file($routeArr[1])){
    throw new Exception("controller is invaild!", 500);
}
$controller = $routeArr[1];
$action = $routeArr[2];

//导入controller文件
$className = $controller.".php";
$file = __DIR__."/".$className;
require $file;


call_user_func([$controller, $action]);
