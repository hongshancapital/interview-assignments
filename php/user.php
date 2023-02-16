<?php

/***
 * 注册业务的逻辑文件
 * 
 * @author Beyond<xc_beyond@163.com>
 * @copyright 2022-9-13
 */


    class user{
        static function register(){
            $userName = array_key_exists('userName', $_POST) ? strval($_POST['userName']) : '';
            $pass = array_key_exists('pass', $_POST) ? strval($_POST['pass']) : '';
            $confirmPass = array_key_exists('confirmPass', $_POST) ? strval($_POST['confirmPass']) : '';
            
            if(empty($userName)){
                throw new Exception("用户名不能为空", 500);
            }
            
            if(!preg_match("/^[a-zA-z][a-zA-Z0-9_]{5,19}$/", $userName)){
                throw new Exception("用户名为大写字母小写字母或下划线,并以字母或者下划线开头,长度为6-20", 500);
            }

            if(empty($pass)){
                throw new Exception("密码不能为空!", 500);
            }

            if(empty($confirmPass)){
                throw new Exception("确认密码不能为空!", 500);
            }


            if($pass != $confirmPass){
                throw new Exception("两次密码不一致!", 500);
            }

            if(!preg_match("/^(?![\d]+$)(?![a-z]+$)(?![A-Z]+$)[\da-zA-z]{6,20}$/", $pass)){
                throw new Exception("密码长度在6-20之间，必须有大写字母，小写字母或数字中的两项!", 500);
            }

            $bool = self::checkPass($pass);
        
            if(!$bool){
                throw new Exception("密码不能含有 3 位或以上的连续数字，如 123、234 等! ", 500);
            }

            //获取表的前缀
            $o_mysql  = new mysql();
            $s_prefix = $o_mysql->getPrefix();
            $pass = md5($pass);

            //存储数据库
            $sql = "INSERT INTO {$s_prefix}user(name, pass) VALUES ('{$userName}', '{$pass}')";
            $id = $o_mysql->create($sql);
            if(empty($id)){
                throw new Exception("创建用户失败！", 500);
            }

            //前端数据回写
            $sql = "SELECT * FROM {$s_prefix}user WHERE id = {$id}";
            $res = $o_mysql->select($sql);
            unset($res['pass']);

            //返回查询结果
            apiResult('success', 200, $res);

        }

        static function checkPass($str){
            //判断是否有三个连续的数字
            for($i=0; $i<strlen($str) - 1; $i++){
                //有一个变量不是纯数字，跳出当前循环
                if(!is_numeric($str[$i-1])|| !is_numeric($str[$i]) || !is_numeric($str[$i+1])){
                    continue;
                }

                $firstIndex = intval($str[$i-1]);
                $secondIndex = intval($str[$i]);
                $thirdIndex = intval($str[$i+1]);
                if(($thirdIndex - $secondIndex == 1)&&($secondIndex - $firstIndex==1)){
                    return false;
                }
            }
            return true;
        }
    }