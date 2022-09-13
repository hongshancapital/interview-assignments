<?php
/***
 * 配置文件
 * 
 * @author Beyond<xc_beyond@163.com>
 * @copyright 2022-9-13
 */
class config {
    
    //读数据库操作的链接方式
    public function read() {
        return array(
            'host' => 'localhost',
            'uname' => 'root',
            'pass' => 'welcome',
            'dbname' => 'test',
            "prefix" => 'hs_'
        );
    }
    
    //写入数据库操作的链接方式
    public function write() {
        return array(
            'host' => 'localhost',
            'uname' => 'root',
            'pass' => 'welcome',
            'dbname' => 'test',
            "prefix" => 'hs_'
        );
    }

}
