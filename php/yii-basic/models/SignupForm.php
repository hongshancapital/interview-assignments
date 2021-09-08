<?php

namespace app\models;

use yii\base\Model;

class SignupForm extends Model
{
    public $username;
    public $password;
    public $repeat_password;

    /**
     * @return array the validation rules.
     */
    public function rules()
    {
        return [
            [['username', 'password','repeat_password'], 'required'],
            ['username', 'validateUsername'],
            ['password', 'validatePassword'],
            ['repeat_password', 'validateRepeatPassword'],
        ];
    }

    public function formName()
    {
        return "";
    }


    /**
     * 用户名验证
     * @param $attribute
     * @param $params
     */
    public function validateUsername($attribute, $params)
    {
        $pattern='/^[a-zA-Z_]\w+$/';
        if(!preg_match($pattern,$this->username)){
            $this->addError($attribute,'用户名不合法');
        }
    }

    /**
     * 密码验证
     * @param $attribute
     * @param $params
     */
    public function validatePassword($attribute, $params)
    {
        $pattern='/^\S{6,}$/';
        if(!preg_match($pattern,$this->password)){
            return $this->addError($attribute,'密码不合法:长度需在 6 位以上');
        }
        $has_09=0;
        $has_AZ=0;
        $has_az=0;

        $i=0;
        $last_num=10;

        $arr=str_split($this->password);
        foreach($arr as $c){
            if(preg_match('/[0-9]/',$c)){
                $has_09=1;
                if($c==$last_num+1){
                    $i++;
                    if($i>=2){
                        return $this->addError($attribute,'密码不合法:不能含有3 位或以上的连续数字');
                    }
                }else{
                    $i=0;
                }
                $last_num=$c;
            }
            if(preg_match('/[A-Z]/',$c)){
                $has_AZ=1;
                $i=0;
                $last_num=10;
            }
            if(preg_match('/[a-z]/',$c)){
                $has_az=1;
                $i=0;
                $last_num=10;
            }
        }

        if(($has_09+$has_AZ+$has_az)<2){
            $this->addError($attribute,'密码不合法:必须有大写字母，小写字母或数字中的两项');
        }
    }

    /**
     * 密码校验
     * @param $attribute
     * @param $params
     */
    public function validateRepeatPassword($attribute, $params)
    {
        if($this->password!==$this->repeat_password){
            $this->addError($attribute,'两次输入的密码不一致');
        }
    }
}
