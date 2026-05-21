<?php
/**
 * Created by PhpStorm.
 * User: maping
 * Date: 2021/8/5
 * Time: 上午11:37
 */
namespace model;

class UserModel extends Model
{
    public static $instance;
    public $tableName = 'user1';

    public static function getInstance()
    {
        if(!self::$instance) self::$instance = new self();
        return self::$instance;
    }

    public function getUser($userName)
    {
        return $this->mysqlConnect->query("Select id from ". $this->tableName." where `username`='{$userName}'")->fetch_all();
    }

    public function addUser($username,$password)
    {
        $time = time();
        $sql = "INSERT INTO ". $this->tableName."  SET username ='{$username}',password= '{$password}',add_at = {$time}";
        return $this->mysqlConnect->query($sql);
    }
}