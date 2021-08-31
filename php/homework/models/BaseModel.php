<?php
class BaseModel{
    static $con = NULL;
    public static function getCon(){
        if (!(self::$con instanceof self)) {
            self::$con = new mysqli("127.0.0.1","admin","Admin!@#123","wuliuce_com");
        }
        if(!self::$con) {
            throw new Exception('数据库连接失败');
        }
        return self::$con;
    }
    private function __construct(){}
    private function __clone(){}
}
