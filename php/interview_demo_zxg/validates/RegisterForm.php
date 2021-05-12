<?php

namespace app\validates;

use app\common\response\StatusCode;
use app\models\User;
use yii\base\Model;

class RegisterForm extends Model
{
    public $username;
    public $password;
    public $repeat_password;
    public $valid_code;

    /**
     * @return array the validation rules.
     */
    public function rules()
    {
        return [
            // username、password、repeat_password required
            [['username', 'password', 'repeat_password'], 'required'],
            ['username', 'validateUsername'],
            ['password', 'validatePassword'],
            [['id', 'valid_code'], 'safe'],
        ];
    }
    public function attributes() {

        $fields = [
            'repeat_password',
            'valid_code',
        ];
        return array_merge(parent::attributes(),$fields);
    }

    /**
     * 校验用户名
     */
    public function validateUsername()
    {
        // 只能以英文字母或下划线开头
        if(!preg_match('/^[A-Za-z_].*$/',$this->username) ) {
            $this->valid_code = StatusCode::$username_invalid_begin;
            $this->addError('username', $this->valid_code['msg']);
        }

        // 只能包含英文字母，下划线或数字
        else if(!preg_match('/\w\d*$/',$this->username) ) {
            $this->valid_code = StatusCode::$username_invalid_include;
            $this->addError('username',$this->valid_code['msg']);
        }
        // 用户名已注册
        else if (User::getUserByUsername($this->username)){
            $this->valid_code = StatusCode::$username_invalid_exist;
            $this->addError('username',$this->valid_code['msg']);
        }
    }

    /**
     * 校验密码
     */
    public function validatePassword()
    {
        // 2次输入的密码是否一致
        if ($this->password != $this->repeat_password) {
            echo $this->password;
            echo $this->repeat_password;
            $this->valid_code = StatusCode::$password_invalid_confirm;
            $this->addError('password',$this->valid_code['msg']);
        }
        // 长度必须 > 6
        else if (strlen($this->password) < 6) {
            $this->valid_code = StatusCode::$password_invalid_length;
            $this->addError('password',$this->valid_code['msg']);
        }
        // 必须有大写字母，小写字母或数字中的两项
        else if (!$this->hasMixed($this->password)) {
            $this->valid_code = StatusCode::$password_invalid_mixed;
            $this->addError('password',$this->valid_code['msg']);
        }
        // 不能含有 3 位以上的连续数字
        else if ($this->hasConsecutiveDigits3($this->password)) {
            $this->valid_code = StatusCode::$password_invalid_consec;
            $this->addError('password',$this->valid_code['msg']);
        }

    }

    /**
     * 是否出现 3位以上的 连续数字
     * @param $str
     * @return bool
     */
    private function hasConsecutiveDigits3($str){
        $regex = '/012|0123|01234|012345|0123456|01234567|012345678|0123456789|123|1234|12345|123456|1234567|12345678|123456789|234|2345|23456|234567|2345678|23456789|345|3456|34567|345678|3456789|456|4567|45678|456789|567|5678|56789|678|6789|789/';
        $matches = [];
        preg_match_all($regex,$str,$matches);
        if (count($matches[0]) > 0 ) {
            return true;
        }
        return false;
    }

    /**
     * 必须有大写字母，小写字母或数字中的两项
     * @param $str
     * @return bool
     */
    private function hasMixed($str) {
        $regex = '/^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[\(\)])+$)([^(0-9a-zA-Z)]|[\(\)]|[a-z]|[A-Z]|[0-9]){6,}$/';
        if (preg_match($regex, $str)){
            return true;
        } else {
            return false;
        }
    }
}
