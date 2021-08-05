<?php
/**
 * Created by PhpStorm.
 * User: maping
 * Date: 2021/8/5
 * Time: 上午11:38
 */
namespace model;
class Model
{
    protected $mysqlConnect;
    public function __construct()
    {
        $connectConfig = [
            'host'=>'', //数据库域名
            'user'=>'', //数据库用户名
            'password'=>'', //数据库密码
            'database'=>'', //数据库名称
            'port'=>'', //端口
        ];
        $this->mysqlConnect = mysqli_connect($connectConfig);
    }


}