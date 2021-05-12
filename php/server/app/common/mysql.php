<?php

namespace app\common;

use think\facade\Db;

class mysql
{
    protected $config = [
        'default'       => 'local',
        'connections'   => [
            'local' => [
                // 数据库类型
                'type'        => 'mysql',
                // 服务器地址
                'hostname'    => '10.2.2.4',
                // 数据库名
                'database'    => 'hongshan',
                // 数据库用户名
                'username'    => 'root',
                // 数据库密码
                'password'    => 'secret20',
                // 数据库连接端口
                'hostport'    => '3306',
                // 数据库连接参数
                'params'      => [],
                // 数据库编码默认采用utf8
                'charset'     => 'utf8',
                // 数据库表前缀
                'prefix'      => '',
            ],
        ],
    ];
    
    /**
     * 初始化
     * @author decezz@qq.com
     * @return void
     */
    public function __construct()
    {
        Db::setConfig($this->config);
    }
}
