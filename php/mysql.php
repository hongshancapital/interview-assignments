<?php
/***
 * 数据库操作类
 * 
 * @author Beyond<xc_beyond@163.com>
 * @copyright 2022-9-13
 */
class mysql{
    private $o_config;
    private $s_prefix;
    
    public function __construct() {
        $this->o_config = new config();
    }
    
    
    //链接数据库
    public function contect($data){
        $o_mysql = mysqli_connect($data['host'], $data['uname'], $data['pass'], $data['dbname']);
        if(!$o_mysql){
            echo '连接数据库失败!';exit;
        }
        $this->setPrefix($data['prefix']);
        
        return $o_mysql;
    }

    //表前缀
    public function getPrefix(){
        if(empty($this->s_prefix)){
            $data = $this->o_config->read();
            $this->setPrefix($data['prefix']);
        }
        return $this->s_prefix;
    }

    public function setPrefix($s_prefix){
        $this->s_prefix = $s_prefix;
    }

    //查询
    public function select($sql){
        $o_mysql = $this->contect($this->o_config->read()); 
        $result = mysqli_query($o_mysql, $sql);
        return mysqli_fetch_array($result, MYSQLI_ASSOC); 
    }

    //修改
    public function create($sql){
        $o_mysql = $this->contect($this->o_config->write()); 
        mysqli_query($o_mysql, $sql);
        return mysqli_insert_id($o_mysql);
    }
    
}