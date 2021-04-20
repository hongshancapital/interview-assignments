<?php

/**
 * @Author   : zhaoyinfan
 * @Date     : 2021-04-16 17:50
 * @Describe : 数据库链接基类
 */
class Connect
{
    //创建私有属性
    private static $instance;

    private $host;

    private $user;

    private $pwd;

    private $port;

    private $db;

    private $charset;

    protected $link;

    //实例化
    public function __construct()
    {
        $dbFile = '../config/db.php';
        $data   = [];
        if (file_exists($dbFile)) {
            $data = include('../config/db.php');
        }
        $this->initArg($data);
        $this->sqlLink();
    }

    public function __clone()
    {
    }

    /**
     * Method createLink
     *
     * @param array $data
     *
     * @return Connect
     * @author  zhaoyinfan
     */
    public static function createLink($data = [])
    {
        if (!self::$instance instanceof self) {
            return self::$instance = new self($data);
        }

        return self::$instance;
    }

    //初始化参数
    private function initArg($data)
    {
        $this->host    = $data['host'] ?? "127.0.0.1";
        $this->user    = $data['user'] ?? "root";
        $this->pwd     = $data['pwd'] ?? "root";
        $this->port    = $data['port'] ?? "3306";
        $this->db      = $data['db'] ?? "my_db";
        $this->charset = $data['charset'] ?? "utf8mb4";
    }

    public function sqlLink()
    {
        //var_dump($this->host, $this->user, $this->pwd, $this->db, $this->port);
        $this->link = mysqli_connect($this->host, $this->user, $this->pwd, $this->db, $this->port);
        if (mysqli_connect_error()) {
            echo "数据库连接失败<br>";
            echo "错误代码" . mysqli_connect_errno();
            exit;
        }
        mysqli_set_charset($this->link, $this->charset);
    }

    public function closeLink()
    {
        if ($this->link) {
            mysqli_close($this->link);
        }
    }
}