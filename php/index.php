<?php
    /***
     * 入口文件
     * 
     * @author Beyond<xc_beyond@163.com>
     * @copyright 2022-9-13
     */
    header("Content-type: text/html; charset=utf-8");
    //error_reporting(E_ERROR | E_WARNING | E_PARSE);
    error_reporting(E_ALL);
    ini_set('date.timezone','Asia/Shanghai');

    try{
        if(!array_key_exists('REQUEST_URI',$_SERVER) || empty($_SERVER['REQUEST_URI'])){
            throw new Exception("REQUEST_URI is empty!", 500);
        }

        $routeArr = explode('/', substr($_SERVER['REQUEST_URI'], 1));
        if(empty($routeArr[0]) || $routeArr[0] != 'api'){
            throw new Exception("url is invaild!", 500);
        }
        
        //导入配置文件
        require __DIR__."/config.php";

        //导入mysql 操作文件
        require __DIR__."/mysql.php";

        //导入路由文件
        require __DIR__."/".$routeArr[0].".php";


    }catch(Exception $exception){
        apiResult($exception->getMessage(), 500);
    }

    function apiResult($message = '', $code, $data = []){
        $arr = array(
            'msg' => $message,
            'code' => $code,
            'data' => $data
        );

        echo json_encode($arr);
    }
